// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

import "./PIP.sol";
import "../contracts/PRP.sol";

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

        // 1. Если уровень доступа ресурса PUBLIC: return true
        if (source.secretLevel == 0) {
            return true;
        }

        // 2. Если уровень доступа ресурса PRIVATE: проверить если пользователь является владельцем ресурса: return true
        if (source.secretLevel == 1) {
            if (source.owner == _userAddress) {
                return true;
            }
            return false;
        }

        // 3. Если уровень доступа ресурса CONFIDENTIAL: проверить если запрашивающий пользователь является владельцем или входит в allowedUsers
        if (source.secretLevel == 2) {
            if (source.owner == _userAddress) {
                return true;
            }
            for (uint i = 0; i < source.allowedUsers.length; i++) {
                if (source.allowedUsers[i] == _userAddress) {
                    return true;
                }
            }
            return false;
        }

        // 4. Если уровень доступа ресурса ARBITRARY: пройтись по списку политик
        if (source.secretLevel == 3) {
            // Получение всех политик
            PRP.Policy[] memory policies = prp.getAllPolicies();

            for (uint i = 0; i < policies.length; i++) {
                PRP.Policy memory policy = policies[i];

                // Если политика определена для определенного типа ресурса
                if (bytes(policy.sourceId).length == 0 && policy.sourceType != 0) {
                    if (policy.sourceType != source.sourceType) {
                        continue;
                    }
                }

                // Если политика для конкретного ресурса
                if (bytes(policy.sourceId).length != 0) {
                    if (keccak256(bytes(policy.sourceId)) != keccak256(bytes(_sourceId))) {
                        continue;
                    }
                }

                // Проверка по ролям и департаментам
                bool roleAllowed = false;
                bool departmentAllowed = false;

                if (policy.allowedRoles.length > 0) {
                    for (uint j = 0; j < policy.allowedRoles.length; j++) {
                        if (policy.allowedRoles[j] == user.role) {
                            roleAllowed = true;
                            break;
                        }
                    }
                }

                if (policy.allowedDepartments.length > 0) {
                    for (uint j = 0; j < policy.allowedDepartments.length; j++) {
                        if (policy.allowedDepartments[j] == user.department) {
                            departmentAllowed = true;
                            break;
                        }
                    }
                }

                if ((policy.allowedRoles.length == 0 || roleAllowed) && (policy.allowedDepartments.length == 0 || departmentAllowed)) {
                    return true;
                }
            }
        }

        return false;
    }
}
