<template>
  <div class="table-container">
    <table class="table table-bordered">
      <thead>
        <tr>
          <th scope="col">#</th>
          <th scope="col">Player</th>
          <th scope="col">XP Delta</th>
          <th scope="col">XP After</th>
          <th scope="col">XP Before</th>

          <th scope="col">Level Delta</th>
          <th scope="col">Level After</th>
          <th scope="col">Level Before</th>

          <th scope="col">Rank Delta</th>
          <th scope="col">Rank After</th>
          <th scope="col">Rank Before</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(item, index) in xpData" :key="item.playerName">
          <th scope="row">{{ index + 1 }}</th>
          <td>{{ item.playerName }}</td>

          <td>{{ item.deltaXp }}</td>
          <td>{{ item.currentXp }}</td>
          <td>{{ item.previousXp }}</td>

          <td>{{ item.deltaLevel }}</td>
          <td>{{ item.currentLevel }}</td>
          <td>{{ item.previousLevel }}</td>

          <td>{{ item.deltaRank }}</td>
          <td>{{ item.currentRank }}</td>
          <td>{{ item.previousRank }}</td>
        </tr>
      </tbody>
    </table>

    <nav aria-label="Page navigation example">
      <ul class="pagination justify-content-center">
        <li class="page-item disabled">
          <a class="page-link" href="#" tabindex="-1">Previous</a>
        </li>
        <li class="page-item"><a class="page-link" href="#">1</a></li>
        <li class="page-item"><a class="page-link" href="#">2</a></li>
        <li class="page-item"><a class="page-link" href="#">3</a></li>
        <li class="page-item">
          <a class="page-link" href="#">Next</a>
        </li>
      </ul>
    </nav>
  </div>
</template>

<script>
import axios from "axios";

export default {
  props: ["dayNumber"],
  name: "DataTable",
  data() {
    return {
      xpData: [],
    };
  },
  methods: {
    getXpData: function (dayNumber) {
      axios.get(`/xp/${dayNumber}`).then((response) => (this.xpData = response.data));
    },
  },
  updated() {
    //this.getXpData(this.$props.dayNumber);
  },
  mounted() {
    this.getXpData(this.$props.dayNumber);
  },
};
</script>

<style scoped></style>
