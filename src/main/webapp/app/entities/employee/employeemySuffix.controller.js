(function() {
    'use strict';

    angular
        .module('trainingApp')
        .controller('EmployeeMySuffixController', EmployeeMySuffixController);

    EmployeeMySuffixController.$inject = ['$scope', '$state', 'Employee'];

    function EmployeeMySuffixController ($scope, $state, Employee) {
        var vm = this;
        
        vm.employees = [];

        loadAll();

        function loadAll() {
            Employee.query(function(result) {
                vm.employees = result;
            });
        }
    }
})();
