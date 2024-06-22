// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

contract PRP {

    struct Policy {
        string policyId;
        string sourceId;
        uint sourceType;
        uint allowedRole;
        uint allowedDepartment;
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
        string memory _sourceId,
        uint _sourceType,
        uint _allowedRole,
        uint _allowedDepartment
    ) public onlyPAP {
        require(bytes(_policyId).length != 0, "Policy ID cannot be empty");
        require(
            bytes(_sourceId).length > 0 || _sourceType != 0,
            "Either sourceId or resourceType must be set"
        );
        require(
            bytes(_sourceId).length == 0 || _sourceType == 0,
            "Either sourceId or resourceType must be set, but not both"
        );
        require(
            _allowedRole != 0 || _allowedDepartment != 0,
            "Either allowedRole or allowedDepartment must be set"
        );

        policies[_policyId] = Policy({
            policyId: _policyId,
            sourceId: _sourceId,
            sourceType: _sourceType,
            allowedRole: _allowedRole,
            allowedDepartment: _allowedDepartment
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