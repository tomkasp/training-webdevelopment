(function() {
    'use strict';

    angular
        .module('trainingApp')
        .controller('DayOffTypeMySuffixDetailController', DayOffTypeMySuffixDetailController);

    DayOffTypeMySuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'DayOffType'];

    function DayOffTypeMySuffixDetailController($scope, $rootScope, $stateParams, previousState, entity, DayOffType) {
        var vm = this;

        vm.dayOffType = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('trainingApp:dayOffTypeUpdate', function(event, result) {
            vm.dayOffType = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
