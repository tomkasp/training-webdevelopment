(function() {
    'use strict';

    angular
        .module('trainingApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('day-off-typemySuffix', {
            parent: 'entity',
            url: '/day-off-typemySuffix',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'DayOffTypes'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/day-off-type/day-off-typesmySuffix.html',
                    controller: 'DayOffTypeMySuffixController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('day-off-typemySuffix-detail', {
            parent: 'entity',
            url: '/day-off-typemySuffix/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'DayOffType'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/day-off-type/day-off-typemySuffix-detail.html',
                    controller: 'DayOffTypeMySuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'DayOffType', function($stateParams, DayOffType) {
                    return DayOffType.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'day-off-typemySuffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('day-off-typemySuffix-detail.edit', {
            parent: 'day-off-typemySuffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/day-off-type/day-off-typemySuffix-dialog.html',
                    controller: 'DayOffTypeMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['DayOffType', function(DayOffType) {
                            return DayOffType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('day-off-typemySuffix.new', {
            parent: 'day-off-typemySuffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/day-off-type/day-off-typemySuffix-dialog.html',
                    controller: 'DayOffTypeMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                description: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('day-off-typemySuffix', null, { reload: 'day-off-typemySuffix' });
                }, function() {
                    $state.go('day-off-typemySuffix');
                });
            }]
        })
        .state('day-off-typemySuffix.edit', {
            parent: 'day-off-typemySuffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/day-off-type/day-off-typemySuffix-dialog.html',
                    controller: 'DayOffTypeMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['DayOffType', function(DayOffType) {
                            return DayOffType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('day-off-typemySuffix', null, { reload: 'day-off-typemySuffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('day-off-typemySuffix.delete', {
            parent: 'day-off-typemySuffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/day-off-type/day-off-typemySuffix-delete-dialog.html',
                    controller: 'DayOffTypeMySuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['DayOffType', function(DayOffType) {
                            return DayOffType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('day-off-typemySuffix', null, { reload: 'day-off-typemySuffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
