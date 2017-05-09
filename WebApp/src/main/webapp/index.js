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

    return console.log(...arguments);
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

            this.$http.post(getActionURL('connexion'), serializeForm(this.form), FORM_CONTENT_TYPE)
                .then(response => response.json())
                .then(response => {
                    // TODO SUCCESS LOGIN
                    // GET DATA FROM response.json().then(data => this.data = data);
                    log(response);
                })
                .catch(response => {
                    // TODO ERROR LOGIN
                    log(response);
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
                email: ''
            }
        }
    },
    methods: {
        signup() {

            this.$http.post(getActionURL('inscription'), serializeForm(this.form), FORM_CONTENT_TYPE)
                .then(response => response.json())
                .then(response => {
                    // TODO SUCCESS SIGNUP
                    // GET DATA FROM response.json().then(data => this.data = data);
                    log(response);
                    this.$refs.snackbarSuccess.open();
                    setTimeout(() => router.push('login'), 4000);

                })
                .catch(response => {
                    // TODO ERROR SIGNUP
                    log(response.json());
                    this.$refs.snackbarFail.open();
                });
        }
    }
};


///////// MY ACCOUNT //////////

const MyAccount = {
    template: '#myaccount-component'
};

const MyAccountMe = {
    template: '#myaccount-me-component',
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
    created() {
        // TODO Load current user
    },
    methods: {
        updateMe() {

            this.$http.post(getActionURL('majInfoClient'), serializeForm(this.form), FORM_CONTENT_TYPE)
                .then(response => response.json())
                .then(response => {
                    // TODO SUCCESS MAJ
                    // GET DATA FROM response.json().then(data => this.data = data);
                    log(response);
                })
                .catch(response => {
                    // TODO ERROR MAJ
                    log(response);
                });
        }
    }
};

const MyAccountBuy = {
    template: '#myaccount-buy-component',
    data: () => {
        return {
            restaurants: [],
            selectedRestaurants: []
        }
    },
    created() {
        this.$http.get(getActionURL("restaurantsPartenaires"))
            .then(response => response.json())
            .then(response => {
                this.restaurants = response;
            })
            .catch(response => {
                // ERROR
            });
    }
};

const MyAccountHistory = {
    template: '#myaccount-history-component',
    data: () => {
        return {
            current: [],
            old: []
        }
    },
    created() {

        this.current = [
            {
                dateEnregistrementCommande: Date.now() - 1000 * 60 * 60 * 24,
                dateLivraisonCommande: Date.now() - 1000 * 60 * 60 * 24,
                id: 120

            }
        ];

        this.old = [
            {
                dateEnregistrementCommande: Date.now() - 1000 * 60 * 60 * 24,
                dateLivraisonCommande: Date.now() - 1000 * 60 * 60 * 24,
                id: 120

            }
        ]

        // this.$http.get(getActionURL("")).then(response => {
        //     this.restaurants = response.json();
        // }, response => {
        //     // ERROR
        // });
    }
};

const About = {
    template: '#about-component'
};

Vue.component('myaccount-me', MyAccountMe);
Vue.component('myaccount-buy', MyAccountBuy);
Vue.component('myaccount-history', MyAccountHistory);
Vue.component('about', About);


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
        component: MyAccount
    },
    {
        path: '/member',
        component: HomeAdmin
    },
    {
        path: '/manager',
        component: Manager
    },
    {
        path: '/delivery',
        component: Delivery
    },
    {
        path: '/dashboard',
        component: Dashboard,
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