(function() {
    'use strict';

    angular
        .module('trainingApp')
        .controller('DaysOffMySuffixController', DaysOffMySuffixController);

    DaysOffMySuffixController.$inject = ['$scope', '$state', 'DaysOff'];

    function DaysOffMySuffixController ($scope, $state, DaysOff) {
        var vm = this;
        
        vm.daysOffs = [];

        loadAll();

        function loadAll() {
            DaysOff.query(function(result) {
                vm.daysOffs = result;
            });
        }
    }
})();
