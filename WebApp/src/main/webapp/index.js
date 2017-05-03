//
//  CONSTANTES
//

const DEBUG_MODE = true;
const SERVLET_PATH = '/ActionServlet';

//
// UTILS
//

function log() {
    if (!DEBUG_MODE) return;

    return console.log(arguments);
}


function getActionURL(action) {
    return SERVLET_PATH + '?action=' + action;
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
// COMPONENTS
//

///////// AUTH //////////

const HomeApp = {
    template: '#home-app-component'
};

const Login = {
    template: '#login-component',
    data: () => {
        return {
            form: {
                mail: '',
                password: ''
            }
        }
    },
    methods: {
        login() {
            let formData = new FormData();

            formData.append('mail', this.mail);
            formData.append('password', this.password);

            this.$http.post(getActionURL('connexion'), formData).then(response => {
                // TODO SUCCESS LOGIN
                // GET DATA FROM response.json().then(data => this.data = data);
            }, response => {
                // TODO ERROR LOGIN
            });
        }
    }
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
        path: '/',
        beforeEnter: (to, from, next) => {
            // TODO Check if connected
            router.replace('/auth');
        }
    },
    {
        path: '/auth', component: HomeApp
    },
    {
        path: '/auth/login',
        component: Login
    },
    {
        path: '/auth/signup',
        component: Signup
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
// ROUTER INSTANCE
//

const router = new VueRouter({
    routes // short for routes: routes
});


//
// MAIN VIEW
//


const vm = new Vue({
    router,
    el: '#app',

    data: () => {
    },

    created: () => {
    },

    methods: {}
});