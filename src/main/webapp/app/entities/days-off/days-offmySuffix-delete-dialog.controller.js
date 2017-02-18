(function() {
    'use strict';

    angular
        .module('trainingApp')
        .controller('DaysOffMySuffixDeleteController',DaysOffMySuffixDeleteController);

    DaysOffMySuffixDeleteController.$inject = ['$uibModalInstance', 'entity', 'DaysOff'];

    function DaysOffMySuffixDeleteController($uibModalInstance, entity, DaysOff) {
        var vm = this;

        vm.daysOff = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            DaysOff.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
