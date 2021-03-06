<template>
  <v-expansion-panel-header
    class="text-h6 hover"
  >
    <v-row align="center">
      <v-col cols="auto">
        <v-simple-checkbox
          v-model="isChecked"
          class="ma-0 mt-1"
          :ripple="false"
          @input="updatingCheckbox"
        />
      </v-col>
      <v-col class="ml-0 pl-0" cols="8">
         <span class="emailText">
           <span class="ma-0 dateAndFrom">
                    {{ emailThread.formattedDate }}  - {{ fromName(emailThread.from) }}
           </span>
           <span class="ml-2 subject">{{ emailThread.subject }}</span>
          </span>
      </v-col>
      <v-col cols="3">
        <span
          v-if="isNotMobile"
          class="float-end label"
        >
          {{ filterLabels }}
        </span>
      </v-col>
    </v-row>

  </v-expansion-panel-header>
</template>
<script>
import screenSizeMixin from '@/mixins/screenSizeMixin';
import { mapGetters } from 'vuex';

export default {
  name: 'emailHeader',
  mixins: [screenSizeMixin],

  props: {
    emailThread: Object,
  },

  data() {
    return {
      isChecked: false,
      labels: [],
    };
  },

  computed: {
    ...mapGetters(['getThreadLabels']),

    filterLabels() {
      const removedCategory = this.labels.filter((label) => !label.includes('CATEGORY'));
      const removedInbox = removedCategory.filter((label) => label !== 'INBOX');
      return removedInbox
        .map((label) => label.toLowerCase())
        .join(',');
    },
  },

  methods: {
    fromName(emailFrom) {
      const startEmail = emailFrom.indexOf('<');
      const charsToRemove = emailFrom.length - startEmail;

      return emailFrom.slice(0, -charsToRemove);
    },

    updateLabels() {
      this.labels = this.getThreadLabels(this.emailThread.id);
    },

    deselect() {
      this.isChecked = false;
    },

    updatingCheckbox() {
      if (this.isChecked) {
        this.$emit('selected', this.emailThread.id);
      } else {
        this.$emit('deselected', this.emailThread.id);
      }
    },
  },

  created() {
    this.updateLabels();
  },
};
</script>
<style scoped>
.hover:hover {
  filter: brightness(150%);
}

.emailText {
  text-align: start;
}

.subject {
  display: inline-block;
  border-left: 2px solid var(--v-secondary-base) !important;
  padding-left: 10px
}

.subject {
  color: var(--v-info-darken2) !important;
}

.label {
  color: var(--v-info-darken4) !important;
}

.dateAndFrom {
  display: inline-block;
  text-overflow: ellipsis;
}

@media only screen and (max-width: 1264px) {
  .emailText {
    width: 100%;
  }

  .dateAndFrom {
    font-size: 0.8em !important;
  }

  .subject {
    font-size: 0.9em !important;
  }

  .label {
    font-size: 0.5em !important;
  }

}

</style>
