package com.manage.composesamples.ui.home.search

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import com.manage.composesamples.model.Filter
import com.manage.composesamples.model.SearchCategoryCollection
import com.manage.composesamples.model.SearchRepo
import com.manage.composesamples.model.SearchSuggestionGroup
import com.manage.composesamples.model.Snack
import com.manage.composesamples.model.SnackRepo

@Composable
fun Search(
    onSnackClick: (Long) -> Unit,
    onNavigateToRoute: (String) -> Unit,
    modifier: Modifier = Modifier,
    state: SearchState = rememberSearchState()
) {

}

@Composable
private fun rememberSearchState(
    query: TextFieldValue = TextFieldValue(""),
    focused: Boolean = false,
    searching: Boolean = false,
    categories: List<SearchCategoryCollection> = SearchRepo.getCategories(),
    suggestions: List<SearchSuggestionGroup> = SearchRepo.getSuggestions(),
    filters: List<Filter> = SnackRepo.getFilters(),
    searchResults: List<Snack> = emptyList()
): SearchState {
    return remember {
        SearchState(
            query = query,
            focused = focused,
            searching = searching,
            categories = categories,
            suggestions = suggestions,
            filters = filters,
            searchResults = searchResults
        )
    }
}

@Stable
class SearchState(
    query: TextFieldValue,
    focused: Boolean,
    searching: Boolean,
    categories: List<SearchCategoryCollection>,
    suggestions: List<SearchSuggestionGroup>,
    filters: List<Filter>,
    searchResults: List<Snack>
) {

}