package course.examples.studyapp;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;

import course.examples.studyapp.TermFragment.ListSelectionListener;

public class MainActivity extends Activity implements ListSelectionListener{

    public static String[] mKeyTerms;
    public static String[] mDefinitions;

    private FragmentManager mFragmentManager;
    private FrameLayout mTermLayout, mDefLayout;
    private DefinitionFragment mDefFrag = new DefinitionFragment();

    private final int MATCH_PARENT = LinearLayout.LayoutParams.MATCH_PARENT;


    //Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //initializing arrays
        mKeyTerms = getResources().getStringArray(R.array.key_terms); //create string-array: key_terms
        mDefinitions = getResources().getStringArray(R.array.term_definitions); //create string-array: term_defintions

        setContentView(R.layout.activity_main);

        //setting up layouts based on layout IDs in activity_main
        mTermLayout = (FrameLayout) findViewById(R.id.term_fg);
        mDefLayout = (FrameLayout) findViewById(R.id.def_fg);

        //reference to the FragmentManager
        mFragmentManager = getFragmentManager();

        //start a new FragmentTransaction
        FragmentTransaction fragmentTransaction = mFragmentManager
                .beginTransaction();

        //add the TitleFragment to the layout
        fragmentTransaction.add(R.id.term_fg,
                new TermFragment());

        //commit the FragmentTransaction
        fragmentTransaction.commit();

        //add a OnBackStackChangedListener to reset the layout when the back stack changes
        mFragmentManager
                .addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
                    public void onBackStackChanged() {
                        setLayout();
                    }
                });

        //initialize spinner
        //spinner = (Spinner) findViewById(R.id.spinner); //where should the spinner id go?

        //spinner.setOnItemLongClickListener();


    }

    public void setLayout(){
        if(!mDefFrag.isAdded()){

            //TermFragment will occupy entire page
            mTermLayout.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT));
            mDefLayout.setLayoutParams(new LinearLayout.LayoutParams(0, MATCH_PARENT));

        } else {

            //TermFragment will occupy 1/3 of page
            //DefinitionFragment will occupy 2/3 of page
            mTermLayout.setLayoutParams(new LinearLayout.LayoutParams(0, MATCH_PARENT, 1f));
            mDefLayout.setLayoutParams(new LinearLayout.LayoutParams(0, MATCH_PARENT, 2f));
        }
    }

    //when the user selects item from TermFragment
    @Override
    public void onListSelection(int index){
        if(!mDefFrag.isAdded()){

            FragmentTransaction ft = mFragmentManager.beginTransaction();
            ft.add(R.id.def_fg, new DefinitionFragment());
            ft.addToBackStack(null);
            ft.commit();
            mFragmentManager.executePendingTransactions();
        }
        if (mDefFrag.getShownIndex() != index) {

            // Tell the QuoteFragment to show the quote string at position index
            mDefFrag.showDefAtIndex(index);

        }
    }

    public String getKeyTerms(int index){
      return mKeyTerms[index];
    }

    //public String[] getTermArr(){
     //   return mKeyTerms;
    //}



}
