<template>
  <table>
    <tbody>
      <tr>
        <th>Last update:&nbsp;</th>
        <td>{{ formatDate(status.initialCheckDateTime) }}</td>
      </tr>
      <tr>
        <th>Last check:&nbsp;</th>
        <td>{{ formatDate(status.lastCheckDateTime) }}</td>
      </tr>
      <tr>
        <th>Last update state:&nbsp;</th>
        <td :class="[isNok ? 'nok' : 'ok']">
          <strong>{{ formatStatus(status.state, status.tryCount) }}</strong>
        </td>
      </tr>
    </tbody>
  </table>
</template>

<script>
import axios from "axios";

export default {
  name: "SyncroResultComponent",
  computed: {
    isNok: function () {
      return this.status.state == null || this.status.state.endsWith("NOK");
    },
  },
  data: function () {
    return {
      status: {
        initialCheckDateTime: null,
        lastCheckDateTime: null,
        state: null,
        tryCount: null,
      },
    };
  },
  methods: {
    requestChanges: () => axios.get(`/syncro-result`),
    handleRequestChangesResponse: function (response) {
      Object.assign(this.status, response.data);
    },

    loadChanges: function (vm) {
      vm.requestChanges().then((response) => vm.handleRequestChangesResponse(response));
    },
    formatStatus: function (state, tryCount) {
      if (state === null) {
        return "";
      }

      if (state === "INIT_OK") {
        return "OK";
      } else if (state === "AFTER_INIT_OK_TO_OK") {
        return "OK";
      } else if (state === "AFTER_INIT_NOK_TO_OK") {
        return `OK [After (${tryCount}) tries]`;
      } else if (state === "AFTER_INIT_NOK_TO_NOK") {
        return `NOK [After (${tryCount}) tries]`;
      } else {
        return "NOK";
      }
    },
    formatDate: function (dateString) {
      if (dateString === null) {
        return null;
      }
      return `${dateString.replace("T", " ")} (UTC)`;
    },
  },
  mounted: function () {
    this.loadChanges(this);
  },
};
</script>

<style scoped>
.nok {
  color: red;
}

.ok {
  color: green;
}
</style>
