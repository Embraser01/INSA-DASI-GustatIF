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

    Object.keys(form).map(e => {
        if (typeof form[e] === "string") kvpairs.push(encodeURIComponent(e) + "=" + encodeURIComponent(form[e]))
    });

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

class AuthService {
    constructor() {
        this.user = null;
    }

    isLogged() {
        return Boolean(this.user);
    }


    signup(form) {
        return Vue.http.post(getActionURL('inscription'), serializeForm(form), FORM_CONTENT_TYPE)
            .then(response => response.json());
    }

    login(form) {
        return Vue.http.post(getActionURL('connexion'), serializeForm(form), FORM_CONTENT_TYPE)
            .then(response => response.json())
            .then(res => {
                return this.user = res;
            });
    }

    logout() {
        return Vue.http.post(getActionURL('deconnexion')).then(res => {
            this.user = null;
            return res;
        });
    }
}

const authService = new AuthService();


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
            authService.login(this.form)
                .then(user => {
                    log(user);
                    router.push('/');
                })
                .catch(error => {
                    // TODO ERROR LOGIN
                    log(error);
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
            authService.signup(this.form)
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
    template: '#myaccount-component',
    methods: {
        logout() {
            log("WHY");
            authService.logout().then(() => {
                router.push('/');
            });
            // TODO ERROR HANDLING
        }
    }
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
            selected: []
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
            router.replace(authService.isLogged() ? '/myaccount' : '/auth');
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

const whitelist = [
    '/auth',
    '/auth/login',
    '/auth/signup',
    '/',

    '/member',
    '/manager',
    '/delivery',
    '/dashboard',
    '/dashboard/history',
    '/dashboard/close'
];

router.beforeEach((to, from, next) => {
    // When not connected, only allow certain routes
    if (authService.isLogged() || whitelist.indexOf(to.path) > -1) return next();
    return next('/');
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