// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

import "../cached/PDP.sol";

contract PEP {
    PDP public pdp;

    constructor(address _pdpAddress) {
        pdp = PDP(_pdpAddress);
    }

    function requestAccess(string memory resourceId) public view returns (bool) {
        return pdp.checkAccess(msg.sender, resourceId);
    }
}
