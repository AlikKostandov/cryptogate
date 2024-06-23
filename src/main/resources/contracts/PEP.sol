// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

import "./PDP.sol";

contract PEP {
    PDP public pdp;

    constructor(address _pdpAddress) {
        pdp = PDP(_pdpAddress);
    }

    function requestAccess(string memory sourceId) public view returns (bool) {
        return pdp.checkAccess(msg.sender, sourceId);
    }
}
