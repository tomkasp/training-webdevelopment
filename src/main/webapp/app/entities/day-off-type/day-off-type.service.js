(function() {
    'use strict';
    angular
        .module('trainingApp')
        .factory('DayOffType', DayOffType);

    DayOffType.$inject = ['$resource'];

    function DayOffType ($resource) {
        var resourceUrl =  'api/day-off-types/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
