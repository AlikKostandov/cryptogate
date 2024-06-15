// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

import "./PIP.sol";
import "./PRP.sol";

contract PDP {
    PIP public pip;
    PRP public prp;

    address private owner;
    address private pepAddress;

    modifier onlyOwner() {
        require(msg.sender == owner, "Only owner can perform this action");
        _;
    }

    function setPEPAddress(address _pepAddress) public onlyOwner {
        pepAddress = _pepAddress;
    }

    modifier onlyPEP() {
        require(msg.sender == pepAddress, "The method can only be accessed via PEP");
        _;
    }

    constructor(address _pipAddress, address _prpAddress) {
        owner = msg.sender;
        pip = PIP(_pipAddress);
        prp = PRP(_prpAddress);
    }

    function checkAccess(address _userAddress, string memory _resourceId) public onlyPEP view returns (bool) {
        PIP.BaseUser memory user = pip.getUser(_userAddress);
        PIP.Source memory resource = pip.getSource(_resourceId);
        if (keccak256(abi.encodePacked(_userAddress)) == keccak256(abi.encodePacked(resource.owner))) {
            return true;
        }

        PRP.Policy[] memory policies = prp.getAllPolicies();

        for (uint i = 0; i < policies.length; i++) {
            PRP.Policy memory policy = policies[i];
            if (policy.sourceType == resource.sourceType && policy.secretLevel == resource.secretLevel) {
                for (uint j = 0; j < policy.allowedRoles.length; j++) {
                    if (user.role == policy.allowedRoles[j]) {
                        return true;
                    }
                }

                for (uint j = 0; j < policy.allowedDepartments.length; j++) {
                    if (user.department == policy.allowedDepartments[j]) {
                        return true;
                    }
                }
            }
        }

        return false;
    }
}
