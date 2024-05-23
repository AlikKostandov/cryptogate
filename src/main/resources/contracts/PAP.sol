// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

contract PAP {

    struct BaseUser {
        string username;
        uint role; // 0: ADMIN, 1: MANAGER, 2: EMPLOYEE, 3: GUEST
        uint department; // 0: IT, 1: HR, 2: FINANCE, 3: SALES, 4: MARKETING, 5: OPERATIONS, 6: CUSTOMER_SERVICE
    }

    mapping(address => BaseUser) private users;

    // Events
    event UserRegistered(address userAddress, string username);

    // Modifier to check if the sender is an admin
    modifier onlyAdmin() {
        require(users[msg.sender].role == 0, "PAP: Only admin can perform this action");
        _;
    }

    // Register a new user (admin only)
    function registerUser(address userAddress, string memory username, uint role, uint department) public onlyAdmin {
        users[userAddress] = BaseUser(username, role, department);
        emit UserRegistered(userAddress, username);
    }

    // Retrieve a user
    function getUser(address userAddress) public view returns (BaseUser memory) {
        return users[userAddress];
    }
}
