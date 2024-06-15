// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

contract PRP {

    struct Policy {
        string policyId;
        uint sourceType;
        uint secretLevel;
        uint[] allowedRoles;
        uint[] allowedDepartments;
    }

    mapping(string => Policy) private policies;
    string[] private policyIds;
    address private owner;
    address private papAddress;

    modifier onlyOwner() {
        require(msg.sender == owner, "Only owner can perform this action");
        _;
    }

    function setPAPAddress(address _papAddress) public onlyOwner {
        papAddress = _papAddress;
    }

    modifier onlyPAP() {
        require(msg.sender == papAddress, "The method can only be accessed via PAP");
        _;
    }

    constructor() {
        owner = msg.sender;
    }

    function storePolicy(
        string memory _policyId,
        uint _sourceType,
        uint _secretLevel,
        uint[] memory _allowedRoles,
        uint[] memory _allowedDepartments
    ) public onlyPAP {
        require(bytes(_policyId).length != 0, "Policy ID cannot be empty");
        require(policies[_policyId].sourceType == 0 && policies[_policyId].secretLevel == 0, "Policy ID already exists");

        policies[_policyId] = Policy({
            policyId: _policyId,
            sourceType: _sourceType,
            secretLevel: _secretLevel,
            allowedRoles: _allowedRoles,
            allowedDepartments: _allowedDepartments
        });
        policyIds.push(_policyId);
    }

    function getPolicy(string memory _policyId)
    public
    view
    returns (Policy memory)
    {
        require(bytes(policies[_policyId].policyId).length != 0, "Policy does not exist");
        return policies[_policyId];
    }

    function removePolicy(string memory _policyId) public onlyPAP {
        require(bytes(policies[_policyId].policyId).length != 0, "Policy does not exist");
        delete policies[_policyId];
        // Remove from policyIds array
        for (uint i = 0; i < policyIds.length; i++) {
            if (keccak256(bytes(policyIds[i])) == keccak256(bytes(_policyId))) {
                policyIds[i] = policyIds[policyIds.length - 1];
                policyIds.pop();
                break;
            }
        }
    }

    function getAllPolicies() public view returns (Policy[] memory) {
        Policy[] memory allPolicies = new Policy[](policyIds.length);
        for (uint i = 0; i < policyIds.length; i++) {
            allPolicies[i] = policies[policyIds[i]];
        }
        return allPolicies;
    }
}