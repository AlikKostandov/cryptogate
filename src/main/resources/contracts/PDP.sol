// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

import "./PIP.sol";
import "./PRP.sol";

contract PDP {

    address public pipContractAddress;
    address public prpContractAddress;

    event AccessDecision(address indexed userAddress, string sourceCID, bool accessGranted);

    constructor(address _pipContractAddress, address _prpContractAddress) {
        pipContractAddress = _pipContractAddress;
        prpContractAddress = _prpContractAddress;
    }

    function evaluateAccess(string memory _userAddress, string memory _sourceCID) public {
        PIP pipContract = PIP(pipContractAddress);
        PRP prpContract = PRP(prpContractAddress);

        PIP.BaseUser memory user = pipContract.getUser(_userAddress);
        PIP.Source memory source = pipContract.getSource(_sourceCID);

        PRP.Policy memory policy = prpContract.getPolicy(0);

        bool accessGranted = evaluatePolicy(policy, user, source);

        emit AccessDecision(msg.sender, _sourceCID, accessGranted);
    }

    function evaluatePolicy(
        PRP.Policy memory _policy,
        PIP.BaseUser memory _user,
        PIP.Source memory _source
    ) private pure returns (bool) {

        bool accessGranted = (block.timestamp % 2 == 0);
        return accessGranted;
    }
}
