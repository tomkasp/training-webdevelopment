(function() {
    'use strict';

    angular
        .module('trainingApp')
        .controller('DaysOffMySuffixDialogController', DaysOffMySuffixDialogController);

    DaysOffMySuffixDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'DaysOff', 'DayOffType', 'Employee'];

    function DaysOffMySuffixDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, DaysOff, DayOffType, Employee) {
        var vm = this;

        vm.daysOff = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.types = DayOffType.query({filter: 'daysoff-is-null'});
        $q.all([vm.daysOff.$promise, vm.types.$promise]).then(function() {
            if (!vm.daysOff.typeId) {
                return $q.reject();
            }
            return DayOffType.get({id : vm.daysOff.typeId}).$promise;
        }).then(function(type) {
            vm.types.push(type);
        });
        vm.employees = Employee.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.daysOff.id !== null) {
                DaysOff.update(vm.daysOff, onSaveSuccess, onSaveError);
            } else {
                DaysOff.save(vm.daysOff, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('trainingApp:daysOffUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.dayOffDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
