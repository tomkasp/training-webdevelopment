(function() {
    'use strict';

    angular
        .module('trainingApp')
        .controller('DayOffTypeMySuffixDialogController', DayOffTypeMySuffixDialogController);

    DayOffTypeMySuffixDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'DayOffType'];

    function DayOffTypeMySuffixDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, DayOffType) {
        var vm = this;

        vm.dayOffType = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.dayOffType.id !== null) {
                DayOffType.update(vm.dayOffType, onSaveSuccess, onSaveError);
            } else {
                DayOffType.save(vm.dayOffType, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('trainingApp:dayOffTypeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
