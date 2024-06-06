// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

import "./PRP.sol";

contract PAP {

    address public prpContractAddress;

    event PolicyCreated(uint id, string name, address createdBy);

    constructor(address _prpContractAddress) {
        prpContractAddress = _prpContractAddress;
    }

    function createPolicy(string memory _name, string memory _description, string memory _rule) public {
        PRP prpContract = PRP(prpContractAddress);
        prpContract.storePolicy(_name, _description, _rule, msg.sender);
        emit PolicyCreated(prpContract.nextPolicyId() - 1, _name, msg.sender);
    }

    function deletePolicy(uint _id) public {
        PRP prpContract = PRP(prpContractAddress);
        prpContract.deletePolicy(_id);
    }

    function getPolicy(uint _id) public view returns (PRP.Policy memory) {
        PRP prpContract = PRP(prpContractAddress);
        return prpContract.getPolicy(_id);
    }

    function getAllPolicies() public view returns (PRP.Policy[] memory) {
        PRP prpContract = PRP(prpContractAddress);
        return prpContract.getAllPolicies();
    }
}
