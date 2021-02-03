<template>
  <div class="table-container">
    <table class="table table-bordered">
      <thead>
        <tr>
          <th scope="col">#</th>
          <th scope="col">Player</th>
          <th scope="col">XP Change</th>
          <th scope="col">XP Current</th>
          <th scope="col">XP Previous</th>

          <th scope="col">Level Change</th>
          <th scope="col">Level Current</th>
          <th scope="col">Level Previous</th>

          <th scope="col">Rank Change</th>
          <th scope="col">Rank Current</th>
          <th scope="col">Rank Previous</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(item, index) in pageChanges" :key="item.playerName">
          <th scope="row">{{ getGlobalRowIndex(index) }}</th>
          <td>{{ item.playerName }}</td>

          <td>{{ toLocaleNumber(item.xpChange) }}</td>
          <td>{{ toLocaleNumber(item.xpCurrent) }}</td>
          <td>{{ toLocaleNumber(item.xpPrevious) }}</td>

          <td>{{ toLocaleNumber(item.levelChange) }}</td>
          <td>{{ toLocaleNumber(item.levelCurrent) }}</td>
          <td>{{ toLocaleNumber(item.levelPrevious) }}</td>

          <td>
            {{ item.rankChange }}
            <i class="bi bi-arrow-up"></i>
          </td>
          <td>{{ item.rankCurrent }}</td>
          <td>{{ item.rankPrevious }}</td>
        </tr>
      </tbody>
    </table>

    <nav aria-label="Page navigation example">
      <ul class="pagination justify-content-center">
        <li class="page-item" :class="{ disabled: isPreviousButtonDisabled() }">
          <a class="page-link" href="#" @click="onPreviousPage()" tabindex="-1">Previous</a>
        </li>

        <li
          v-for="pageNr in pageCount"
          :key="pageNr"
          class="page-item"
          :class="{ active: isActivePage(pageNr) }"
        >
          <a class="page-link" href="#" @click="onSelectPage(pageNr)">
            {{ pageNr }}
          </a>
        </li>

        <li class="page-item" :class="{ disabled: isNextButtonDisabled() }">
          <a class="page-link" href="#" @click="onNextPage()">Next</a>
        </li>
      </ul>
    </nav>
  </div>
</template>

<script>
import axios from "axios";

export default {
  props: ["daysBeforeToday"],
  name: "PlayerOverallStateChangeTableComponent",

  data() {
    return {
      playersOverallChanges: [],
      pageSize: 10,
      selectedPageNr: 1,
    };
  },

  computed: {
    pageChanges() {
      return this.playersOverallChanges.slice(this.pageBeginIndex, this.pageEndIndex);
    },

    pageCount() {
      return Math.ceil(this.playersOverallChanges.length / this.pageSize);
    },

    pageBeginIndex() {
      return (this.selectedPageNr - 1) * this.pageSize;
    },

    pageEndIndex() {
      return this.isLastPage ? this.lastPageEndIndex : this.selectedPageNr * this.pageSize;
    },

    lastPageEndIndex() {
      return (
        (this.playersOverallChanges.length % this.pageSize) + this.selectedPageNr * this.pageSize
      );
    },

    isLastPage() {
      return this.selectedPageNr == this.pageCount;
    },
  },

  methods: {
    requestChanges: (vm) => axios.get(`/overall-state-change/${vm.$props.daysBeforeToday}`),
    handleRequestChangesResponse: (response, vm) => (vm.playersOverallChanges = response.data),

    loadChanges: (vm) =>
      vm.requestChanges(vm).then((response) => vm.handleRequestChangesResponse(response, vm)),

    onSelectPage: function (selectedPageNr) {
      this.selectedPageNr = selectedPageNr;
    },

    onPreviousPage: function () {
      this.selectedPageNr = this.selectedPageNr - 1;
    },

    onNextPage: function () {
      this.selectedPageNr = this.selectedPageNr + 1;
    },

    getGlobalRowIndex: function (localRowIndex) {
      return this.pageSize * (this.selectedPageNr - 1) + (localRowIndex + 1);
    },

    isActivePage: function (index) {
      return this.selectedPageNr === index;
    },

    isNextButtonDisabled: function () {
      return this.selectedPageNr === this.pageCount;
    },

    isPreviousButtonDisabled: function () {
      return this.selectedPageNr === 1;
    },

    toLocaleNumber: function (number) {
      return parseInt(number).toLocaleString();
    },
  },

  beforeRouteEnter(_to, _, next) {
    next((vm) => vm.loadChanges(vm));
  },
};
</script>

<style scoped>
/* https://stackoverflow.com/questions/32355009/changing-pagination-colour-bootstrap */
.pagination > li > a {
  background-color: white;
  color: #007bff;
}

.pagination > li > a:focus,
.pagination > li > a:hover,
.pagination > li > span:focus,
.pagination > li > span:hover {
  color: #5a5a5a;
  background-color: #eee;
  border-color: #ddd;
}

.pagination > .active > a {
  color: white;
  background-color: #007bff !important;
  border: solid 1px #007bff !important;
}

.pagination > .active > a:hover {
  background-color: #007bff !important;
  border: solid 1px #007bff;
}
</style>
