(function() {
    'use strict';

    angular
        .module('trainingApp')
        .controller('DayOffTypeMySuffixDeleteController',DayOffTypeMySuffixDeleteController);

    DayOffTypeMySuffixDeleteController.$inject = ['$uibModalInstance', 'entity', 'DayOffType'];

    function DayOffTypeMySuffixDeleteController($uibModalInstance, entity, DayOffType) {
        var vm = this;

        vm.dayOffType = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            DayOffType.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
