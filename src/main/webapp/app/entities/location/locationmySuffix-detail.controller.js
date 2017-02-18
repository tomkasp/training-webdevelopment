(function() {
    'use strict';

    angular
        .module('trainingApp')
        .controller('LocationMySuffixDetailController', LocationMySuffixDetailController);

    LocationMySuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Location', 'Country'];

    function LocationMySuffixDetailController($scope, $rootScope, $stateParams, previousState, entity, Location, Country) {
        var vm = this;

        vm.location = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('trainingApp:locationUpdate', function(event, result) {
            vm.location = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
