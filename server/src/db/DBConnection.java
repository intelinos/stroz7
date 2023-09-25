package db;

import Organization.*;
import exceptions.InvalidPasswordException;
import exceptions.PermisionException;
import exceptions.UserAlreadyRegisteredException;
import exceptions.UserDoesNotExistException;
import response.Response;

import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;

import static java.sql.Types.*;

public class DBConnection {
    private Connection connection;

    protected DBConnection(String url, String login, String password) throws SQLException {
        this.connection = DriverManager.getConnection(url, login, password);
    }
    public void loginUser(String login, String password) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM users WHERE login = ?");
        ps.setString(1, login);
        ResultSet result = ps.executeQuery();
        if (result.next()) {
            String salt = result.getString("salt");
            byte[] dbHashedPassword = result.getBytes("password");
            if (!Hasher.checkPassword(password, salt, dbHashedPassword)) {
                throw new InvalidPasswordException("Неверный пароль.");
            }
        } else {
            throw new UserDoesNotExistException("Вы не зарегистрированы в системе.");
        }
    }
    public void registerUser(String login, String password) throws SQLException {
        if (isUserRegistered(login)) {
            throw new UserAlreadyRegisteredException("Вы уже зарегистрированы в системе.");
        }
        PreparedStatement ps = connection.prepareStatement("INSERT INTO users (login, password, salt) VALUES (?, ?, ?)");
        String salt = Hasher.getSalt();
        byte[] hashedPassword = Hasher.getHash(password, salt);
        ps.setString(1, login);
        ps.setBytes(2, hashedPassword);
        ps.setString(3, salt);
        ps.executeUpdate();
    }
    private boolean isUserRegistered(String login) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("SELECT 1 FROM users WHERE login = ?");
        ps.setString(1, login);
        ResultSet result = ps.executeQuery();
        if (result.next()) {
            return true;
        } return false;
    }
    public ConcurrentSkipListMap<Integer, Organization> getCollectionFromDB() throws SQLException{
        ConcurrentSkipListMap<Integer, Organization> collection = new ConcurrentSkipListMap<>();
        PreparedStatement ps = connection.prepareStatement(
                "SELECT organization.*, coordinates.x, coordinates.y, address.street, address.zipCode  FROM organization " +
                        "JOIN coordinates on organization.coordinatesID = coordinates.id " +
                        "LEFT JOIN address on organization.addressID = address.id"
        );
        ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                Integer key = resultSet.getInt("key");
                Organization organization = getOrganizationFromResultSet(resultSet);
                collection.put(key, organization);
            }
        //System.out.println(collection);
        return collection;
    }
    private Organization getOrganizationFromResultSet(ResultSet resultSet) throws SQLException{
        Integer id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        Coordinates coordinates = new Coordinates(resultSet.getLong("x"), resultSet.getDouble("y"));
        LocalDateTime creationDate = resultSet.getTimestamp("creationDate").toLocalDateTime();
        float annualTurnover = resultSet.getFloat("annualTurnover");
        long employeesCount = resultSet.getLong("employeesCount");
        OrganizationType type;
        if (resultSet.getString("type")==null)
            type=null;
        else type = OrganizationType.valueOf(resultSet.getString("type"));
        Address address = new Address(resultSet.getString("street"), resultSet.getString("zipCode"));
        Organization organization = new Organization(name, coordinates, annualTurnover, employeesCount, type, address);
        organization.setCreationDate(creationDate);
        organization.setId(id);
        return organization;
    }
    public void insertOwner(int key, String login) throws SQLException{
        PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO owners (organizationKey, ownerLogin) VALUES (?, ?)");
        ps.setInt(1, key);
        ps.setString(2, login);
        ps.executeUpdate();
    }
    public int insertCoordinates(Coordinates coordinates) throws SQLException{
        PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO coordinates (x, y) VALUES (?, ?) RETURNING id");
        ps.setLong(1, coordinates.getX());
        ps.setDouble(2, coordinates.getY());
        ResultSet resultSet = ps.executeQuery();
        int id = -1;
        if (resultSet.next()) {
            id = resultSet.getInt(1);
        }
        return id;
    }
    public int insertAddress(Address address) throws SQLException{
        PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO address (street, zipCode) VALUES (?, ?) RETURNING id");
        ps.setString(1, address.getStreet());
        ps.setString(2, address.getZipCode());
        ResultSet resultSet = ps.executeQuery();
        int id = -1;
        if (resultSet.next()) {
            id = resultSet.getInt(1);
        }
        return id;
    }
    public int insertOrganizationAndOwner(int key, Organization organization, String login) throws SQLException{
        PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO organization (key, name, coordinatesID, annualTurnover, employeesCount , type, addressID) " +
                        "VALUES (?, ?, ?, ?, ?, CAST(? AS organizationType), ?) RETURNING id");
        ps.setInt(1, key);
        ps.setString(2, organization.getName());
        ps.setInt(3, insertCoordinates(organization.getCoordinates()));
        ps.setFloat(4, organization.getAnnualTurnover());
        ps.setLong(5, organization.getEmployeesCount());
        if (organization.getType()==null)
            ps.setNull(6, OTHER);
        else
            ps.setString(6, organization.getType().name());
        if (organization.getPostalAddress()==null)
            ps.setNull(7, INTEGER);
        else
            ps.setInt(7, insertAddress(organization.getPostalAddress()));
        ResultSet resultSet = ps.executeQuery();
        insertOwner(key, login);
        int id = -1;
        if (resultSet.next()) {
            id = resultSet.getInt(1);
        }
        return id;
    }
    public List<Integer> clearOwnerOrganizations(String login) throws SQLException{
        List<Integer> ownerOrganizationsKeys = new LinkedList<>();
        PreparedStatement /*ps = connection.prepareStatement(
                "SELECT key FROM organization " +
                        "JOIN owners on organization.key = owners.organizationKey " +
                        "WHERE ownerLogin = ?");
        ps.setString(1, login);
        ResultSet resultSet = ps.executeQuery();
        while (resultSet.next()) {
            ownerOrganizationsKeys.add(resultSet.getInt("key"));
        }*/
        ps = connection.prepareStatement(
                "DELETE FROM organization WHERE key IN (" +
                        "SELECT key FROM organization " +
                        "JOIN owners on organization.key = owners.organizationKey " +
                        "WHERE ownerLogin = ?) RETURNING key");
        ps.setString(1, login);
        ResultSet resultSet= ps.executeQuery();
        while(resultSet.next())
            ownerOrganizationsKeys.add(resultSet.getInt("key"));
        return ownerOrganizationsKeys;
    }
    public boolean removeOrganization(int key) throws SQLException{
        PreparedStatement ps = connection.prepareStatement(
                "DELETE FROM organization WHERE key = ?");
        ps.setInt(1, key);
        if (ps.executeUpdate()==1) {
            return true;
        }
        return false;
    }
    public boolean checkOrganizationOwner(int key, String login) {
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT ownerLogin FROM owners WHERE organizationKey = ?");
            ps.setInt(1, key);
            ResultSet resultSet = ps.executeQuery();
            String loginInDB = null;
            if (resultSet.next()) {
                loginInDB = resultSet.getString("ownerLogin");
            }
            if (!loginInDB.equals(login))
                return false;
            return true;
        } catch (SQLException e){
            return false;
        }

    }
    public void updateFullOrganization(int id, Organization organization) throws SQLException{
        PreparedStatement ps = connection.prepareStatement(
                "SELECT key FROM organization WHERE id = ?");
        ps.setInt(1, id);
        ResultSet resultSet = ps.executeQuery();
        int key = -1;
        if (resultSet.next()) {
            key = resultSet.getInt("key");
        }
        updateCoordinates(key, organization.getCoordinates());
        updateOrganization(key, organization);
        updateAddress(key, organization.getPostalAddress());
    }
    public void updateOrganization(int key, Organization organization) throws SQLException{
        PreparedStatement ps = connection.prepareStatement(
                "UPDATE organization SET (name, annualTurnover, employeesCount, type) = " +
                        "(?, ?, ?, CAST(? AS organizationType)) WHERE key = ?");
        ps.setString(1, organization.getName());
        ps.setFloat(2, organization.getAnnualTurnover());
        ps.setLong(3, organization.getEmployeesCount());
        if (organization.getType()==null)
            ps.setNull(4, OTHER);
        else
            ps.setString(4, organization.getType().name());
        ps.setInt(5, key);
        ps.executeUpdate();
        if(organization.getPostalAddress()==null){
            ps = connection.prepareStatement(
                    "UPDATE organization SET addressID = ? WHERE key = ?");
            ps.setNull(1, INTEGER);
            ps.setInt(2, key);
            ps.executeUpdate();
        }
    }
    public void updateCoordinates(int key, Coordinates coordinates) throws SQLException{
        /*PreparedStatement ps = connection.prepareStatement(
                "SELECT coordinatesID FROM organization WHERE key = ?");
        ps.setInt(1, key);
        ResultSet resultSet = ps.executeQuery();
        int coordinatesID = -1;
        if (resultSet.next()) {
            coordinatesID = resultSet.getInt("coordinatesID");
        }*/
        PreparedStatement ps = connection.prepareStatement(
                "UPDATE coordinates SET (x, y) = (?, ?) WHERE id IN ( " +
                        "SELECT coordinatesID " +
                        "FROM organization "+
                        "WHERE key = ?)");
        ps.setLong(1, coordinates.getX());
        ps.setDouble(2, coordinates.getY());
        ps.setInt(3, key);
        ps.executeUpdate();
    }
    public void updateAddress(int key, Address address) throws SQLException{
        if (address==null) {
            PreparedStatement ps = connection.prepareStatement(
                    "DELETE FROM address WHERE id IN (" +
                            "SELECT addressID " +
                            "FROM organization " +
                            "WHERE key = ?)");
            ps.setInt(1, key);
            ps.executeUpdate();
        } else {
            PreparedStatement ps = connection.prepareStatement(
                    "UPDATE address SET (street, zipCode) = (?, ?) WHERE id IN ( " +
                            "SELECT addressID " +
                            "FROM organization "+
                            "WHERE key = ?)");
            ps.setString(1, address.getStreet());
            ps.setString(2, address.getZipCode());
            ps.setInt(3, key);
            ps.executeUpdate();
        }
    }
    public int replaceOrganization(int key, Organization organization, String login) throws SQLException{
        removeOrganization(key);
        return insertOrganizationAndOwner(key, organization, login);
    }
}