package com.project.prediction_hub

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.project.prediction_hub.common_helper.DefaultHelper.hideKeyboard
import com.project.prediction_hub.common_helper.DefaultHelper.openFragment
import com.project.prediction_hub.common_helper.DefaultHelper.showToast
import com.project.prediction_hub.common_helper.OnCurrentFragmentVisibleListener
import com.project.prediction_hub.databinding.ActivityMainBinding
import com.project.prediction_hub.ui.about_us.AboutUsFragment
import com.project.prediction_hub.ui.home.HomeFragment
import com.project.prediction_hub.ui.home.MatchDetailFragment
import com.project.prediction_hub.ui.privacy_policy.PrivacyPolicyFragment
import com.project.prediction_hub.ui.terms_condition.TermsConditionFragment


class MainActivity : AppCompatActivity(), OnCurrentFragmentVisibleListener {

    private var mBinding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding!!.root)

        init()
        backPressManager()
        manageClickEvents()
    }

    private fun init() {
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimaryDark)
        setSupportActionBar(mBinding?.appBarMain?.toolbar)
        mBinding?.appBarMain?.toolbar?.setNavigationIcon(R.drawable.ic_menu_24)
        mBinding?.appBarMain?.fab?.setOnClickListener { view ->
            shareApplication()
        }

        mBinding?.appBarMain?.toolbar?.setNavigationOnClickListener {
            if (supportFragmentManager.backStackEntryCount > 0) {
                supportFragmentManager.popBackStack()
            } else {
                openDrawer()
            }
        }
        openFragment(this, HomeFragment(), false)
    }


    @SuppressLint("WrongConstant")
    private fun openDrawer() {
        hideKeyboard(this, mBinding?.drawerLayout)
        if (mBinding?.drawerLayout?.isDrawerVisible(Gravity.START) == true) {
            mBinding?.drawerLayout?.closeDrawer(Gravity.START, true)
        } else {
            mBinding?.drawerLayout?.openDrawer(Gravity.START, true)
        }
    }

    private fun manageClickEvents() {
        mBinding?.navView?.tvHome?.setOnClickListener {
            openDrawer()
            openFragment(this, HomeFragment(), false)
        }
        mBinding?.navView?.tvAboutUs?.setOnClickListener {
            openDrawer()
            openFragment(this, AboutUsFragment(), true)
        }
        mBinding?.navView?.tvRateUs?.setOnClickListener {
            openDrawer()
            rateApp()
        }
        mBinding?.navView?.tvPrivacyPolicy?.setOnClickListener {
            openDrawer()
            openFragment(this, PrivacyPolicyFragment(), true)
        }
        mBinding?.navView?.tvTnc?.setOnClickListener {
            openDrawer()
            openFragment(this, TermsConditionFragment(), true)
        }
    }

    private fun backPressManager() {
        supportFragmentManager.addOnBackStackChangedListener {
            try {
                if (supportFragmentManager.backStackEntryCount > 0) {
                    mBinding?.appBarMain?.toolbar?.setNavigationIcon(R.drawable.ic_back_24)
                    mBinding?.appBarMain?.fab?.visibility = View.GONE
                } else {
                    mBinding?.appBarMain?.toolbar?.setNavigationIcon(R.drawable.ic_menu_24)
                    mBinding?.appBarMain?.fab?.visibility = View.VISIBLE
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onAttachFragment(fragment: Fragment) {
        super.onAttachFragment(fragment)
        if (fragment is HomeFragment) fragment.setOnCurrentFragmentVisibleListener(this)
        if (fragment is MatchDetailFragment) fragment.setOnCurrentFragmentVisibleListener(this)
        if (fragment is AboutUsFragment) fragment.setOnCurrentFragmentVisibleListener(this)
        if (fragment is PrivacyPolicyFragment) fragment.setOnCurrentFragmentVisibleListener(this)
        if (fragment is TermsConditionFragment) fragment.setOnCurrentFragmentVisibleListener(this)

    }

    override fun onSetToolbarTitle(show: Boolean, currentFragmentName: String) {
        when (currentFragmentName) {
            HomeFragment::class.java.simpleName -> {
                setToolbarName(getString(R.string.menu_home))
            }
            MatchDetailFragment::class.java.simpleName -> {
                setToolbarName(getString(R.string.match_details))
            }
            AboutUsFragment::class.java.simpleName -> {
                setToolbarName(getString(R.string.menu_about_us))
            }
            PrivacyPolicyFragment::class.java.simpleName -> {
                setToolbarName(getString(R.string.menu_privacy_policy))
            }
            TermsConditionFragment::class.java.simpleName -> {
                setToolbarName(getString(R.string.menu_terms_condition))
            }
        }
    }

    private fun setToolbarName(titleName: String) {
        mBinding?.appBarMain?.toolbar?.title = titleName
    }

    private fun rateApp() {
        //val uri = Uri.parse("market://details?id=com.whatsapp")
        val uri = Uri.parse("market://details?id=$packageName")
        println("playStoreUrl: $uri")
        val myAppLinkToMarket = Intent(Intent.ACTION_VIEW, uri)
        try {
            startActivity(myAppLinkToMarket)
        } catch (e: ActivityNotFoundException) {
            showToast(this, "Impossible to find an application for the market")
        }
    }

    private fun shareApplication() {
        //val link = "https://play.google.com/store/apps/details?id=com.whatsapp"
        val link = Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
        val uri = applicationContext.getString(R.string.kindly_find_the_application) + "\n\n" + link
        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.type = "text/plain"
        shareIntent.putExtra(
            Intent.EXTRA_TEXT,
            uri
        )
        startActivity(Intent.createChooser(shareIntent, getString(R.string.send_to)))
    }


}