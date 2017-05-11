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
        if (typeof form[e] === "string"
            || typeof form[e] === "number")
            kvpairs.push(encodeURIComponent(e) + "=" + encodeURIComponent(form[e]))
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

class UserService {
    constructor() {

        this.user = null;

        if (DEBUG_MODE) {
            this.login({
                email: 'romain.mie@free.fr',
                password: 138
            }).then(() => {
                router.push('/');
            });
        }
    }

    isLogged() {
        return Boolean(this.user);
    }

    getUser() {
        return this.user;
    }

    signup(form) {
        return Vue.http.post(getActionURL('inscription'), serializeForm(form), FORM_CONTENT_TYPE)
            .then(response => response.json());
    }

    login(form) {
        return Vue.http.post(getActionURL('connexion'), serializeForm(form), FORM_CONTENT_TYPE)
            .then(response => response.json())
            .then(user => {
                return this.user = user;
            });
    }

    update(newUser) {
        return Vue.http.post(getActionURL('majInfoClient'), serializeForm(newUser), FORM_CONTENT_TYPE)
            .then(response => response.json())
            .then(user => {
                return this.user = user;
            })
    }

    logout() {
        return Vue.http.post(getActionURL('deconnexion')).then(res => {
            this.user = null;
            return res;
        });
    }
}

const userService = new UserService();


const HomeApp = {
    template: '#home-app-component'
};

const Login = {
    template: '#login-component',
    data: () => {
        return {
            form: {
                email: '',
                password: ''
            }
        }
    },
    methods: {
        login() {
            userService.login(this.form)
                .then(user => {
                    log(user);
                    router.push('/');
                })
                .catch(error => {
                    this.$refs.snackbarFail.open();
                    this.form.password = '';
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
            userService.signup(this.form)
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
            userService.logout().then(() => {
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
                email: ''
            }
        }
    },
    created() {
        let user = userService.getUser();
        this.form = {
            name: user.nom,
            surname: user.prenom,
            address: user.adresse,
            email: user.mail
        };

    },
    methods: {
        updateMe() {

            userService.update(this.form)
                .then(response => {
                    // TODO SUCCESS UPDATE
                    // GET DATA FROM response.json().then(data => this.data = data);
                    log(response);
                    this.$refs.snackbarSuccess.open();

                })
                .catch(response => {
                    // TODO ERROR UPDATE
                    log(response.json());
                    this.$refs.snackbarFail.open();
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

    methods: {
        select() {
            log("Restaurant selectionner : ", this.restaurants[this.selected]);
            // TODO OPEN DIALOG
        }
    },
    created() {
        this.$http.get(getActionURL("restaurantsPartenaires"))
            .then(response => response.json())
            .then(response => {
                this.restaurants = response;
            })
            .catch(response => {
                // TODO ERROR HANDLING
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
                dateEnregistrementCommande: moment("13/12/2016 13:08:22", "DD-MM-YYYY HH:mm:ss"),
                dateLivraisonCommande: null,
                id: 810

            },
            {
                dateEnregistrementCommande: moment("15/01/2017 13:08:22", "DD-MM-YYYY HH:mm:ss"),
                dateLivraisonCommande: null,
                id: 812

            }
        ];

        this.old = [
            {
                dateEnregistrementCommande: moment("13/12/2016 13:08:22", "DD-MM-YYYY HH:mm:ss"),
                dateLivraisonCommande: moment("13/12/2016 14:22:56", "DD-MM-YYYY HH:mm:ss"),
                id: 803

            },
            {
                dateEnregistrementCommande: moment("15/01/2017 13:08:22", "DD-MM-YYYY HH:mm"),
                dateLivraisonCommande: moment("15/01/2017 14:22:56", "DD-MM-YYYY HH:mm:ss"),
                id: 805

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
            router.replace(userService.isLogged() ? '/myaccount' : '/auth');
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
    if (userService.isLogged() || whitelist.indexOf(to.path) > -1) return next();
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