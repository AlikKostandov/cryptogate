// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

import "./PRP.sol";

contract PAP {
    PRP public prp;
    address private owner;

    event PolicyStored(string policyId, uint sourceType, address createdBy);
    event PolicyDeleted(string policyId);

    modifier onlyOwner() {
        require(msg.sender == owner, "Only owner can perform this action");
        _;
    }

    constructor(address _prpAddress) {
        owner = msg.sender;
        prp = PRP(_prpAddress);
    }

    function createPolicy(
        string memory _policyId,
        string memory _sourceId,
        uint _sourceType,
        uint _allowedRole,
        uint _allowedDepartment
    ) public onlyOwner {
        prp.storePolicy(
            _policyId,
            _sourceId,
            _sourceType,
            _allowedRole,
            _allowedDepartment);
        emit PolicyStored(_policyId, _sourceType, owner);
    }


    function removePolicy(string memory _policyId) public onlyOwner {
        prp.removePolicy(_policyId);
        emit PolicyDeleted(_policyId);
    }

    function getAllPolicies() public view returns (PRP.Policy[] memory) {
        return prp.getAllPolicies();
    }

    function getPolicy(string memory _policyId)
    public
    view
    returns (PRP.Policy memory)
    {
        return prp.getPolicy(_policyId);
    }

}
