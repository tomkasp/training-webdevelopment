(function() {
    'use strict';
    angular
        .module('trainingApp')
        .factory('DaysOff', DaysOff);

    DaysOff.$inject = ['$resource', 'DateUtils'];

    function DaysOff ($resource, DateUtils) {
        var resourceUrl =  'api/days-offs/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.dayOffDate = DateUtils.convertDateTimeFromServer(data.dayOffDate);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
