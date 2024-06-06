// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

contract PIP {

    struct BaseUser {
        string userAddress;
        string username;
        bytes32 passwordHash;
        uint role; // 0: ADMIN, 1: MANAGER, 2: EMPLOYEE, 3: GUEST
        uint department; // 0: IT, 1: HR, 2: FINANCE, 3: SALES, 4: MARKETING, 5: OPERATIONS, 6: CUSTOMER_SERVICE
    }

    struct Source {
        string sourceCID;
        string owner;
        uint sourceType;
        uint secretLevel; // 0: PUBLIC, 1: INTERNAL, 2: CONFIDENTIAL, 3: SECRET
        string[] sourceTags;
    }

    mapping(string => BaseUser) public users;
    mapping(string => Source) public sources;

    event UserRegistered(string userAddress);
    event SourceRegistered(string sourceCID);

    function registerUser(
        string memory _userAddress,
        string memory _username,
        bytes32 _passwordHash,
        uint _role,
        uint _department
    ) public {
        users[_userAddress] = BaseUser(_userAddress, _username, _passwordHash, _role, _department);
        emit UserRegistered(_userAddress);
    }

    function registerSource(
        string memory _sourceCID,
        string memory _owner,
        uint _sourceType,
        uint _secretLevel,
        string[] memory _sourceTags
    ) public {
        sources[_sourceCID] = Source(_sourceCID, _owner, _sourceType, _secretLevel, _sourceTags);
        emit SourceRegistered(_sourceCID);
    }

    function getUser(string memory _userAddress) public view returns (BaseUser memory) {
        return users[_userAddress];
    }

    function getSource(string memory _sourceCID) public view returns (Source memory) {
        return sources[_sourceCID];
    }
}
