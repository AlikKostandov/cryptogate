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

    function checkAccess(address _userAddress, string memory _sourceId) public onlyPEP view returns (bool) {
        PIP.BaseUser memory user = pip.getUser(_userAddress);
        PIP.Source memory source = pip.getSource(_sourceId);

        if (source.secretLevel == 1) {
            return true;
        }

        if (source.owner == _userAddress) {
            return true;
        }

        if (source.secretLevel == 3) {
            if (source.owner == _userAddress || source.allowedUser == _userAddress) {
                return true;
            }
            return false;
        }

        if (source.secretLevel == 2) {
            PRP.Policy[] memory policies = prp.getAllPolicies();

            for (uint i = 0; i < policies.length; i++) {
                PRP.Policy memory policy = policies[i];

                // Если политика определена для определенного типа ресурса
                if (source.sourceType == policy.sourceType) {
                    bool roleAllowed = policy.allowedRole == 0 || policy.allowedRole == user.role;
                    bool departmentAllowed = policy.allowedDepartment == 0 || policy.allowedDepartment == user.department;

                    if (roleAllowed && departmentAllowed) {
                        return true;
                    }
                }

                if (bytes(policy.sourceId).length != 0) {
                    if (keccak256(bytes(policy.sourceId)) == keccak256(bytes(source.sourceId))) {
                        bool roleAllowed = policy.allowedRole == 0 || policy.allowedRole == user.role;
                        bool departmentAllowed = policy.allowedDepartment == 0 || policy.allowedDepartment == user.department;

                        if (roleAllowed && departmentAllowed) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
