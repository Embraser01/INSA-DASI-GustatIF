//
//  CONSTANTES
//

const DEBUG_MODE = true;
const SERVLET_PATH = '/ActionServlet';
const FORM_CONTENT_TYPE = {
    headers: {
        "Content-Type": "application/x-www-form-urlencoded"
    }
};

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


function serializeForm(form) {
    const kvpairs = [];

    Object.keys(form).map(e => kvpairs.push(encodeURIComponent(e) + "=" + encodeURIComponent(form[e])));

    return kvpairs.join("&");
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

            this.$http.post(getActionURL('connexion'), serializeForm(this.form), FORM_CONTENT_TYPE).then(response => {
                // TODO SUCCESS LOGIN
                // GET DATA FROM response.json().then(data => this.data = data);
                log(response.json());
            }, response => {
                // TODO ERROR LOGIN
                log(response.json());
            });
        }
    }
};

const Signup = {
    template: '#signup-component',
    data: () => {
        return {
            form: {
                name: '',
                surname: '',
                address: '',
                mail: ''
            }
        }
    },
    methods: {
        signup() {

            this.$http.post(getActionURL('inscription'), serializeForm(this.form), FORM_CONTENT_TYPE).then(response => {
                // TODO SUCCESS SIGNUP
                // GET DATA FROM response.json().then(data => this.data = data);
                log(response.json());
            }, response => {
                // TODO ERROR SIGNUP
                log(response.json());
            });
        }
    }
};


///////// MY ACCOUNT //////////

const MyAccount = {
    template: '#myaccount-component'
};

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

Vue.component('myaccount-me', MyAccountMe);
Vue.component('myaccount-buy', MyAccountBuy);
Vue.component('myaccount-history', MyAccountHistory);
Vue.component('about', About);


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
        path: '/auth',
        component: HomeApp
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
        path: '/myaccount',
        component: MyAccount    // TODO Component
    },
    {
        path: '/cart', component: ShoppingCart    // TODO Component
    },
    {
        path: '/member',
        component: HomeAdmin    // TODO Component
    },
    {
        path: '/manager',  // TODO Component
        component: Manager
    },
    {
        path: '/delivery',
        component: Delivery    // TODO Component
    },
    {
        path: '/dashboard',    // TODO Component
        component: Dashboard,
        children: [
            {
                path: 'history', component: DeliveryHistory    // TODO Component
            },
            {
                path: 'close', component: CloseDelivery    // TODO Component
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