'use strict';

describe('Controller Tests', function() {

    describe('DaysOff Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockDaysOff, MockDayOffType, MockEmployee;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockDaysOff = jasmine.createSpy('MockDaysOff');
            MockDayOffType = jasmine.createSpy('MockDayOffType');
            MockEmployee = jasmine.createSpy('MockEmployee');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'DaysOff': MockDaysOff,
                'DayOffType': MockDayOffType,
                'Employee': MockEmployee
            };
            createController = function() {
                $injector.get('$controller')("DaysOffMySuffixDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'trainingApp:daysOffUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
