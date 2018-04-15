var app = angular.module('chatApp', ['ngMaterial']);
app.config(function ($mdThemingProvider) {
    $mdThemingProvider.theme('default')
        .primaryPalette('purple')
        .accentPalette('green');
});
app.controller('chatController', function ($scope) {
    $scope.messages = [];
    var exampleSocket = new WebSocket('ws://localhost:9000/chatSocket');
    exampleSocket.onmessage = function (event) {
        console.log(event.data);
    };
    $scope.sendMessage = function () {
        exampleSocket.send($scope.userMessage);
        $scope.userMessage = '';
    };
});