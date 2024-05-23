// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

contract AccessControl {

    struct User {
        string username;
        string password;
        Role[] roles;
    }

    enum Role {Admin, User}

    mapping(address => User) private users;
    mapping(address => bool) private adminRoles;

    event UserRegistered(address userAddress, string username);
    event UserDeleted(address userAddress);
    event UserRoleAdded(address userAddress, Role role);
    event UserRoleRemoved(address userAddress, Role role);

    constructor() {
        adminRoles[msg.sender] = true; // Contract deployer is an admin
    }

    modifier onlyAdmin() {
        require(adminRoles[msg.sender], "Only admin can call this function");
        _;
    }

    function registerUser(string memory _username, string memory _password) public {
        require(bytes(_username).length > 0, "Username cannot be empty");
        require(bytes(_password).length > 0, "Password cannot be empty");
        require(bytes(users[msg.sender].username).length == 0, "User already registered");

        users[msg.sender].username = _username;
        users[msg.sender].password = _password;
        emit UserRegistered(msg.sender, _username);
    }

    function deleteUser(address _userAddress) public onlyAdmin {
        require(bytes(users[_userAddress].username).length > 0, "User not found");

        delete users[_userAddress];
        emit UserDeleted(_userAddress);
    }

    function addUserRole(address _userAddress, Role _role) public onlyAdmin {
        require(bytes(users[_userAddress].username).length > 0, "User not found");

        users[_userAddress].roles.push(_role);
        emit UserRoleAdded(_userAddress, _role);
    }

    function removeUserRole(address _userAddress, Role _role) public onlyAdmin {
        require(bytes(users[_userAddress].username).length > 0, "User not found");

        for (uint i = 0; i < users[_userAddress].roles.length; i++) {
            if (users[_userAddress].roles[i] == _role) {
                delete users[_userAddress].roles[i];
                emit UserRoleRemoved(_userAddress, _role);
                return;
            }
        }
    }

    function authenticate(string memory _username, string memory _password) public view returns (bool) {
        User memory user = users[msg.sender];
        return keccak256(bytes(user.username)) == keccak256(bytes(_username)) &&
               keccak256(bytes(user.password)) == keccak256(bytes(_password));
    }

    function hasRole(address _userAddress, Role _role) public view returns (bool) {
        User memory user = users[_userAddress];
        for (uint i = 0; i < user.roles.length; i++) {
            if (user.roles[i] == _role) {
                return true;
            }
        }
        return false;
    }
}
