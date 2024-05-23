pragma solidity ^0.8.0;

contract AccessControl {

    struct AccessRights {
        address employee;
        address authorizedEmployee;
        bool accessGranted;
    }

    mapping(bytes32 => AccessRights) public accessMap;

    function grantAccess(address _authorizedEmployee, bytes32 _dataHash) public {
        require(!accessMap[_dataHash].accessGranted, "Access already granted");

        accessMap[_dataHash] = AccessRights(msg.sender, _authorizedEmployee, true);
    }

    function revokeAccess(bytes32 _dataHash) public {
        require(accessMap[_dataHash].accessGranted, "Access not granted");

        delete accessMap[_dataHash];
    }

    function hasAccess(address _employee, bytes32 _dataHash) public view returns (bool) {
        return (accessMap[_dataHash].authorizedEmployee == _employee && accessMap[_dataHash].accessGranted);
    }
}
