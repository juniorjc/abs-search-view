package com.actionbarsherlock.widget.searchview;

import com.actionbarsherlock.widget.searchview.internal.CompatSearchView;
import com.actionbarsherlock.widget.searchview.internal.ForwardingSearchView;
import com.actionbarsherlock.widget.searchview.internal.ISearchView;

import android.annotation.TargetApi;
import android.app.SearchableInfo;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.LinearLayout;

public class SearchView extends ForwardingSearchView {
    
    public SearchView(Context context) {
        this(context, null);
    }

    public SearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    
    @Override
    protected ISearchView createSearchView(Context context, AttributeSet attrs) {
        if(Build.VERSION.SDK_INT <= 10) {
            return new CompatSearchView(context, attrs);
        } else {
            return new NativeSearchViewAdapter(context, attrs);
        }
    }
    
    /**
     * Some of the methods are only supported since 14 or 16 api levels. 
     * This implementation does not try to backport this functionality 
     * to all api levels since 11. If something is not implemented at current level, it will 
     * throw exception at runtime (which is currently caught and printed to log).
     */
    @TargetApi(11)
    private static class NativeSearchViewAdapter extends LinearLayout implements ISearchView {
        
        private static final String TAG = android.widget.SearchView.class.getSimpleName();
        
        /* TODO: do we want to handle such errors? For now: yes. */
        private static final boolean HANDLE_NO_SUCH_METHOD_ERROR = true;
        private static final String HANDLE_MSG = "SearchView's %s() is not supported on this platform.";
        
        private android.widget.SearchView mNativeSearchView;
              
        public NativeSearchViewAdapter(Context context, AttributeSet attrs) {
            super(context, attrs);
            
            mNativeSearchView = new android.widget.SearchView(context, attrs);
            addView(mNativeSearchView);
        }

        @TargetApi(14)
        @Override
        public void setImeOptions(int imeOptions) {
            try {
                mNativeSearchView.setImeOptions(imeOptions);
            } catch(NoSuchMethodError ex) {
                if (HANDLE_NO_SUCH_METHOD_ERROR) {
                    Log.e(TAG, String.format(HANDLE_MSG, "setImeOptions"), ex);
                } else {
                    throw ex;
                }
            }
        }

        @TargetApi(16)
        @Override
        public int getImeOptions() {
            try {
                return mNativeSearchView.getImeOptions();
            } catch(NoSuchMethodError ex) {
                if (HANDLE_NO_SUCH_METHOD_ERROR) {
                    Log.e(TAG, String.format(HANDLE_MSG, "getImeOptions"), ex);
                    return -1;
                } else {
                    throw ex;
                }
            }
        }

        @TargetApi(14)
        @Override
        public void setInputType(int inputType) {
            try {
                mNativeSearchView.setInputType(inputType);
            } catch(NoSuchMethodError ex) {
                if (HANDLE_NO_SUCH_METHOD_ERROR) {
                    Log.e(TAG, String.format(HANDLE_MSG, "setInputType"), ex);
                } else {
                    throw ex;
                }
            }
        }

        @TargetApi(16)
        @Override
        public int getInputType() {
            try {
                return mNativeSearchView.getInputType();
            } catch(NoSuchMethodError ex) {
                if (HANDLE_NO_SUCH_METHOD_ERROR) {
                    Log.e(TAG, String.format(HANDLE_MSG, "getInputType"), ex);
                    return -1;
                } else {
                    throw ex;
                }
            }
        }

        @Override
        public CharSequence getQuery() {
            return mNativeSearchView.getQuery();
        }

        @Override
        public void setQuery(CharSequence query, boolean submit) {
            mNativeSearchView.setQuery(query, submit);
        }

        @Override
        public void setQueryHint(CharSequence hint) {
            mNativeSearchView.setQueryHint(hint);
        }

        @TargetApi(16)
        @Override
        public CharSequence getQueryHint() {
            try {
                return mNativeSearchView.getQueryHint();
            } catch(NoSuchMethodError ex) {
                if (HANDLE_NO_SUCH_METHOD_ERROR) {
                    Log.e(TAG, String.format(HANDLE_MSG, "getQueryHint"), ex);
                    return null;
                } else {
                    throw ex;
                }
            }
        }

        @Override
        public void setIconifiedByDefault(boolean iconified) {
            mNativeSearchView.setIconifiedByDefault(iconified);
        }

        @Override
        public boolean isIconfiedByDefault() {
            return mNativeSearchView.isIconfiedByDefault();
        }

        @Override
        public void setIconified(boolean iconify) {
            mNativeSearchView.setIconified(iconify);
        }

        @Override
        public boolean isIconified() {
            return mNativeSearchView.isIconified();
        }

        @Override
        public void setSubmitButtonEnabled(boolean enabled) {
            mNativeSearchView.setSubmitButtonEnabled(enabled);            
        }

        @Override
        public boolean isSubmitButtonEnabled() {
            return mNativeSearchView.isSubmitButtonEnabled();
        }

        @Override
        public void setQueryRefinementEnabled(boolean enable) {
            mNativeSearchView.setQueryRefinementEnabled(enable);
        }

        @Override
        public boolean isQueryRefinementEnabled() {
            return mNativeSearchView.isQueryRefinementEnabled();
        }

        @Override
        public void setSuggestionsAdapter(CursorAdapter adapter) {
            mNativeSearchView.setSuggestionsAdapter(adapter);
        }

        @Override
        public CursorAdapter getSuggestionsAdapter() {
            return mNativeSearchView.getSuggestionsAdapter();
        }

        @Override
        public void setMaxWidth(int maxpixels) {
            mNativeSearchView.setMaxWidth(maxpixels);
        }

        @TargetApi(16)
        @Override
        public int getMaxWidth() {
            try {
                return mNativeSearchView.getMaxWidth();
            } catch(NoSuchMethodError ex) {
                if (HANDLE_NO_SUCH_METHOD_ERROR) {
                    Log.e(TAG, String.format(HANDLE_MSG, "getMaxWidth"), ex);
                    return -1;
                } else {
                    throw ex;
                }
            }
        }

        @Override
        public void setSearchableInfo(SearchableInfo searchable) {
            mNativeSearchView.setSearchableInfo(searchable);    
        }
        
        @Override
        public void setFocusable(boolean focusable) {
            mNativeSearchView.setFocusable(focusable);            
        }
        
        @Override
        public void clearFocus() {
            mNativeSearchView.clearFocus();
        }

        @Override
        public void setOnQueryTextListener(final OnQueryTextListener listener) {
            mNativeSearchView.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {
                
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return listener.onQueryTextSubmit(query);
                }
                
                @Override
                public boolean onQueryTextChange(String newText) {
                    return listener.onQueryTextChange(newText);
                }
            });
        }

        @Override
        public void setOnCloseListener(final OnCloseListener listener) {
            mNativeSearchView.setOnCloseListener(new android.widget.SearchView.OnCloseListener() {
                @Override
                public boolean onClose() {
                    return listener.onClose();
                }
            });
        }

        @Override
        public void setOnQueryTextFocusChangeListener(final OnFocusChangeListener listener) {
            mNativeSearchView.setOnQueryTextFocusChangeListener(new OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    listener.onFocusChange(v, hasFocus);
                }
            });
        }

        @Override
        public void setOnSuggestionListener(final OnSuggestionListener listener) {
            mNativeSearchView.setOnSuggestionListener(new android.widget.SearchView.OnSuggestionListener() {
                @Override
                public boolean onSuggestionSelect(int position) {
                    return listener.onSuggestionSelect(position);
                }
                
                @Override
                public boolean onSuggestionClick(int position) {
                    return listener.onSuggestionClick(position);
                }
            });
        }

        @Override
        public void setOnSearchClickListener(final OnClickListener listener) {
            mNativeSearchView.setOnSearchClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(v);
                }
            });
        }
    }
}
