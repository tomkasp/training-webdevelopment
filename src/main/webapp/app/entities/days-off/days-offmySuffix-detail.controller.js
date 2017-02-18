(function() {
    'use strict';

    angular
        .module('trainingApp')
        .controller('DaysOffMySuffixDetailController', DaysOffMySuffixDetailController);

    DaysOffMySuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'DaysOff', 'DayOffType', 'Employee'];

    function DaysOffMySuffixDetailController($scope, $rootScope, $stateParams, previousState, entity, DaysOff, DayOffType, Employee) {
        var vm = this;

        vm.daysOff = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('trainingApp:daysOffUpdate', function(event, result) {
            vm.daysOff = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
