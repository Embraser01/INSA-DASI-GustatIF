//
//  CONSTANTES
//

const DEBUG_MODE = true;

//
// UTILS
//

function log() {
    if (!DEBUG_MODE) return;

    return console.log(arguments);
}

//
// GLOBAL VARIABLES AND INIT
//

Vue.config.devtools = true;

// Include Vue material & Vue router & Vue resource
Vue.use(VueMaterial);
Vue.use(VueRouter);
Vue.use(VueResource);


//
// ROUTER INSTANCE
//

const router = new VueRouter({
    routes // short for routes: routes
});


//
// COMPONENTS
//

///////// AUTH //////////

const HomeApp = {
    template: '#home-app-component'
};

const Login = {
    template: '#login-component'
};

const Signup = {
    template: '#signup-component'
};


///////// MY ACCOUNT //////////

const MyAccountMe = {
    template: '#myaccount-me-component'
};

const MyAccountBuy = {
    template: '#myaccount-buy-component'
};

const MyAccountHistory = {
    template: '#myaccount-history-component'
};

const About = {
    template: '#about-component'
};


////////// SHOPPING CART //////////////

const ShoppingCart = {
    template: '#shopping-cart-component'
};


////////// ADMINISTRATION //////////////

const HomeAdmin = {
    template: '#home-admin-component'
};

const Manager = {
    template: '#manager-component'
};

const Delivery = {
    template: '#delivery-component'
};

////////// DASHBOARD //////////////

const Dashboard = {
    template: '#dashboard-component'
};

const Manager = {
    template: '#manager-component'
};

const DeliveryHistory = {
    template: '#delivery-history-component'
};

const CloseDelivery = {
    template: '#close-delivery-component'
};


//
// ROUTES
//

const routes = [
    {
        path: '/auth', component: HomeApp,
        children: [
            {
                path: 'login',
                component: Login
            },
            {
                path: 'signup',
                component: Signup
            }
        ]
    },
    {
        path: '/myaccount', redirect: '/myaccount/me',
        children: [
            {
                path: 'me',
                component: MyAccountMe
            },
            {
                path: 'buy',
                component: MyAccountBuy
            },
            {
                path: 'history',
                component: MyAccountHistory
            },
            {
                path: 'about',
                component: About
            }
        ]
    },
    {
        path: '/cart', component: ShoppingCart
    },
    {
        path: '/member', component: HomeAdmin,
        children: [
            {
                path: 'manager', component: Manager
            },
            {
                path: 'delivery', component: Delivery
            }
        ]
    },
    {
        path: '/dashboard', component: Dashboard,
        children: [
            {
                path: 'history', component: DeliveryHistory
            },
            {
                path: 'close', component: CloseDelivery
            }
        ]
    }
];

//
// MAIN VIEW
//


const vm = new Vue({
    router,
    el: '#app',

    data: {},

    created: function () {
    },

    methods: {}
});