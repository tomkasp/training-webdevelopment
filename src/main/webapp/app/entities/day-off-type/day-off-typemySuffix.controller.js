(function() {
    'use strict';

    angular
        .module('trainingApp')
        .controller('DayOffTypeMySuffixController', DayOffTypeMySuffixController);

    DayOffTypeMySuffixController.$inject = ['$scope', '$state', 'DayOffType'];

    function DayOffTypeMySuffixController ($scope, $state, DayOffType) {
        var vm = this;
        
        vm.dayOffTypes = [];

        loadAll();

        function loadAll() {
            DayOffType.query(function(result) {
                vm.dayOffTypes = result;
            });
        }
    }
})();
