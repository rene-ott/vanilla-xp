<template>
  <navigation-bar v-on:selected="onNavItemSelected"/>
  <data-table/>
</template>

<script>
import NavigationBar from './components/NavigationBar.vue'
import DataTable from './components/DataTable.vue'
import axios from 'axios';


export default {
  name: 'App',
  components: {
    DataTable,
    NavigationBar
  },
  beforeMount() {
    console.log(process.env);

  },
  methods: {
    onNavItemSelected: function(navItem) {
      console.log(navItem);
      axios
      .get(`/xp/${navItem}`)
      .then(response => (console.log(response)))
    }
  }
}
</script>