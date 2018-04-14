var app = angular.module('chatApp', ['ngMaterial']);

app.controller('chatController', function ($scope) {
    $scope.messages = [
        {
            'sender': 'USER',
            'text': 'Hello Bot!'
    	},
        {
            'sender': 'BOT',
            'text': 'Hello user! How can I help you?'
    	},
        {
            'sender': 'USER',
            'text': 'Give me some news about Donald Trump'
    	},
        {
            'sender': 'BOT',
            'text': 'Here is news about Donald Trump'
    	},
        {
            'sender': 'USER',
            'text': 'Thank you'
    	}
	];
});