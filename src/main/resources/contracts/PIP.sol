// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

contract PIP {
    struct UserAttributes {
        uint role;
        uint department;
    }

    struct ObjectAttributes {
        uint sourceType;
        uint secretLevel;
    }

    mapping(address => UserAttributes) private userAttributes;
    mapping(bytes32 => ObjectAttributes) private objectAttributes;

    // Methods for user attributes
    function setUserAttributes(address userAddress, uint role, uint department) public {
        userAttributes[userAddress] = UserAttributes(role, department);
    }

    function getUserAttributes(address userAddress) public view returns (uint, uint) {
        UserAttributes memory attributes = userAttributes[userAddress];
        return (attributes.role, attributes.department);
    }

    function setObjectAttributes(bytes32 objectCID, uint sourceType, uint secretLevel) public {
        objectAttributes[objectCID] = ObjectAttributes(sourceType, secretLevel);
    }

    function getObjectAttributes(bytes32 objectCID) public view returns (uint, uint) {
        ObjectAttributes memory attributes = objectAttributes[objectCID];
        return (attributes.sourceType, attributes.secretLevel);
    }
}
