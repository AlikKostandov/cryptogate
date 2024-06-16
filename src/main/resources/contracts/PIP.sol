// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

contract PIP {

    struct BaseUser {
        address userAddress;
        string username;
        uint role;
        uint department;
    }

    struct Source {
        string sourceId;
        address owner;
        uint sourceType;
        uint secretLevel;
        string[] sourceTags;
    }

    mapping(address => BaseUser) private users;
    mapping(string => Source) private sources;
    address[] private userAddresses;

    address private owner;

    modifier onlyOwner() {
        require(msg.sender == owner, "Only owner can perform this action");
        _;
    }

    constructor() {
        owner = msg.sender;
    }

    function addUser(
        address _userAddress,
        string memory _username,
        uint _role,
        uint _department
    ) public onlyOwner {
        require(_role <= 3, "Invalid role");
        require(_department <= 6, "Invalid department");
        require(users[_userAddress].userAddress == address(0), "User already exists");

        users[_userAddress] = BaseUser({
            userAddress: _userAddress,
            username: _username,
            role: _role,
            department: _department
        });
        userAddresses.push(_userAddress);
    }

    function getUser(address _userAddress)
    public
    view
    returns (BaseUser memory)
    {
        require(users[_userAddress].userAddress != address(0), "User does not exist");
        return users[_userAddress];
    }

    function removeUser(address _userAddress) public onlyOwner {
        require(users[_userAddress].userAddress != address(0), "User does not exist");
        delete users[_userAddress];

        for (uint i = 0; i < userAddresses.length; i++) {
            if (userAddresses[i] == _userAddress) {
                userAddresses[i] = userAddresses[userAddresses.length - 1];
                userAddresses.pop();
                break;
            }
        }
    }

    function getAllUsers() public view returns (BaseUser[] memory) {
        BaseUser[] memory allUsers = new BaseUser[](userAddresses.length);
        for (uint i = 0; i < userAddresses.length; i++) {
            allUsers[i] = users[userAddresses[i]];
        }
        return allUsers;
    }

    function addSource(
        string memory _sourceId,
        address _owner,
        uint _sourceType,
        uint _secretLevel,
        string[] memory _sourceTags
    ) public onlyOwner {
        require(_sourceType <= 7, "Invalid source type");
        require(_secretLevel <= 3, "Invalid secret level");
        require(users[_owner].userAddress != address(0), "Owner does not exist");
        require(bytes(sources[_sourceId].sourceId).length == 0, "Source already exists");

        sources[_sourceId] = Source({
            sourceId: _sourceId,
            owner: _owner,
            sourceType: _sourceType,
            secretLevel: _secretLevel,
            sourceTags: _sourceTags
        });
    }

    function getSource(string memory _sourceId)
    public
    view
    returns (Source memory)
    {
        require(bytes(sources[_sourceId].sourceId).length != 0, "Source does not exist");
        return sources[_sourceId];
    }

    function removeSource(string memory _sourceId) public onlyOwner {
        require(bytes(sources[_sourceId].sourceId).length != 0, "Source does not exist");
        delete sources[_sourceId];
    }
}
