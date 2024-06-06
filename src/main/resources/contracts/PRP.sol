// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

contract PRP {

    struct Policy {
        uint id;
        string name;
        string description;
        string rule;
        address createdBy;
    }

    Policy[] public policies;
    uint public nextPolicyId;

    event PolicyStored(uint id, string name, address createdBy);

    function storePolicy(string memory _name, string memory _description, string memory _rule, address _createdBy) public {
        policies.push(Policy(nextPolicyId, _name, _description, _rule, _createdBy));
        emit PolicyStored(nextPolicyId, _name, _createdBy);
        nextPolicyId++;
    }

    function getPolicy(uint _id) public view returns (Policy memory) {
        require(_id < nextPolicyId, "Policy does not exist");
        return policies[_id];
    }

    function getAllPolicies() public view returns (Policy[] memory) {
        return policies;
    }
}
