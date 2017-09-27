/**
 * Created by MohdRazif on 10/14/2016.
 */

angular
    .module('app')
    .config(config);
config.$inject=['$routeProvider'];

function config($routeProvider) {
    $routeProvider.
    /* Profile */
    when('/', {template: '<div oc-lazy-load="_apps/profile/user_view.cmp.js"><user-view></user-view></div>'}).
    when('/profile', {template: '<div oc-lazy-load="_apps/profile/user_view.cmp.js"><user-view></user-view></div>'}).
    //when('/profile', {templateUrl: '_apps/profile/view.html'}).
    when('/session/add', {template: '<div oc-lazy-load="_apps/session/session_add.cmp.js"><session-add></session-add></div>'}).
    when('/session/list', {template: '<div oc-lazy-load="_apps/session/session_list.cmp.js"><session-list></session-list></div>'}).
    when('/session/:id', {template: '<div oc-lazy-load="_apps/session/session_view.cmp.js"><session-view></session-view></div>'}).
    when('/session/edit/:id', {template: '<div oc-lazy-load="_apps/session/session_edit.cmp.js"><session-detail></session-detail></div>'}).
    when('/user/:username', {templateUrl: '_apps/user_view.html'}).
    when('/profile', {template: '<div oc-lazy-load="_apps/profile/user_view.cmp.js"><user-view></user-view></div>'});


    // when('/', {redirectTo:'/admin/start'}).
    // when('/admin/start', {template: '<div oc-lazy-load="_apps/start/start.cmp.js"><start></start></div>', reloadOnSearch:"false"}).
    // when('/admin/activity', {template: '<div oc-lazy-load="_apps/activity/list.cmp.js"><activity-list></activity-list></div>', reloadOnSearch:"false"}).
    // when('/admin/approval', {template: '<div oc-lazy-load="_apps/approval/list.cmp.js"><approval-list></approval-list></div>', reloadOnSearch:"false"}).
    // // when('/cico/:id', {template: '<div oc-lazy-load="app/cico/edit.cmp.js"><cico-edit></cico-edit></div>', reloadOnSearch:"false"}).
    // when('/admin/devconsole', {template: '<div oc-lazy-load="_apps/devconsole/list.cmp.js"><dev-console></dev-console></div>', reloadOnSearch:"false"}).
    // when('/admin/devconsole/:clientId', {template: '<div oc-lazy-load="_apps/devconsole/detail.cmp.js"><client-detail></client-detail></div>', reloadOnSearch:"false"}).
    // when('/admin/about', {template: '<div oc-lazy-load="_apps/about/about.cmp.js"><about></about></div>', reloadOnSearch:"false"});
}
