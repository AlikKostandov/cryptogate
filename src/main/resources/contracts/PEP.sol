// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

// Импортируем интерфейс контракта PDP
import "./PDP.sol";

contract PEP {

    // Адрес контракта PDP
    address public pdpContractAddress;

    // Событие для уведомления о принятии решения о доступе
    event AccessDecision(address indexed userAddress, string sourceCID, bool accessGranted);

    // Устанавливаем адрес контракта PDP в конструкторе
    constructor(address _pdpContractAddress) {
        pdpContractAddress = _pdpContractAddress;
    }

    // Функция для запроса решения о доступе у контракта PDP
    function requestAccessDecision(string memory _userAddress, string memory _sourceCID, uint _policyId) public {
        // Получаем экземпляр контракта PDP
        PDP pdpContract = PDP(pdpContractAddress);

        // Вызываем функцию контракта PDP для оценки доступа
        pdpContract.evaluateAccess(_userAddress, _sourceCID, _policyId);
    }

    // Функция для обработки решения о доступе от контракта PDP
    function handleAccessDecision(bool accessGranted) external {
        // Пример обработки решения о доступе
        emit AccessDecision(msg.sender, _sourceCID, accessGranted);
        // Здесь может быть дополнительная логика обработки решения о доступе
    }
}
