// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

import "./PIP.sol";

contract PAP {

    struct BaseUser {
        string username;
        bytes32 passwordHash;
        uint role; // 0: ADMIN, 1: MANAGER, 2: EMPLOYEE, 3: GUEST
        uint department; // 0: IT, 1: HR, 2: FINANCE, 3: SALES, 4: MARKETING, 5: OPERATIONS, 6: CUSTOMER_SERVICE
    }

    struct Source {
        string sourceCID;
        address owner;
        string[] sourceTags;
    }

    mapping(address => BaseUser) private users;
    mapping(bytes32 => Source) private sources;
    address private admin;
    PIP private pip;

    modifier onlyAdmin() {
        require(msg.sender == admin, "Only admin can perform this action");
        _;
    }

    modifier onlyOwner(bytes32 sourceCID) {
        require(sources[sourceCID].owner == msg.sender, "Only owner can perform this action");
        _;
    }

    constructor(address pipAddress) {
        admin = msg.sender;
        pip = PIP(pipAddress);
    }

    // User management functions
    function registerUser(address userAddress, string memory username, string memory password, uint role, uint department) public onlyAdmin {
        require(bytes(users[userAddress].username).length == 0, "User already exists");
        users[userAddress] = BaseUser(username, keccak256(abi.encodePacked(password)), role, department);
        pip.setUserAttributes(userAddress, role, department);
    }

    function getUser(address userAddress) public view returns (string memory, uint, uint) {
        BaseUser memory user = users[userAddress];
        require(bytes(user.username).length != 0, "User does not exist");
        return (user.username, user.role, user.department);
    }

    function authenticateUser(address userAddress, string memory password) public view returns (bool) {
        BaseUser memory user = users[userAddress];
        require(bytes(user.username).length != 0, "User does not exist");
        return user.passwordHash == keccak256(abi.encodePacked(password));
    }

    // Source management functions
    function addSource(bytes32 sourceCID, string memory sourceCIDString, address owner, uint sourceType, uint secretLevel, string[] memory sourceTags) public onlyAdmin {
        require(sources[sourceCID].owner == address(0), "Source already exists");
        sources[sourceCID] = Source(sourceCIDString, owner, sourceTags);
        pip.setObjectAttributes(sourceCID, sourceType, secretLevel);
    }

    function getSource(bytes32 sourceCID) public view returns (string memory, address, string[] memory, uint, uint) {
        Source memory source = sources[sourceCID];
        require(source.owner != address(0), "Source does not exist");
        (uint sourceType, uint secretLevel) = pip.getObjectAttributes(sourceCID);
        return (source.sourceCID, source.owner, source.sourceTags, sourceType, secretLevel);
    }

    function updateSource(bytes32 sourceCID, uint sourceType, uint secretLevel, string[] memory sourceTags) public onlyOwner(sourceCID) {
        Source storage source = sources[sourceCID];
        require(source.owner != address(0), "Source does not exist");
        source.sourceTags = sourceTags;
        pip.setObjectAttributes(sourceCID, sourceType, secretLevel);
    }

    function transferOwnership(bytes32 sourceCID, address newOwner) public onlyOwner(sourceCID) {
        require(sources[sourceCID].owner != address(0), "Source does not exist");
        sources[sourceCID].owner = newOwner;
    }
}
