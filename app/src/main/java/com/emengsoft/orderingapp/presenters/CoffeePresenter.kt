package com.emengsoft.orderingapp.presenters

import android.util.Log
import com.emengsoft.orderingapp.contracts.CoffeeContract
import com.emengsoft.orderingapp.models.CoffeeOrder
import com.google.firebase.FirebaseException
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

/**
 * Created by Fajar Agung Pramana on 06 January 2019
 * - Emengsoft Studio
 * - Indonesia
 */
 
class CoffeePresenter(private val mView: CoffeeContract.View) : CoffeeContract.Presenter {

    companion object {
        private const val TAG = "CoffeePresenter"
    }

    private val mCoffees = mutableListOf<CoffeeOrder>()

    override fun getCoffeeMenu() {

        // reference to menu "coffee" on Firebase database
        val dbRef = FirebaseDatabase.getInstance().reference
            .child("res")
            .child("menu")
            .child("coffee")

        try {

            mView.showLoading()

            dbRef.addValueEventListener(object : ValueEventListener {

                override fun onCancelled(databaseError: DatabaseError) {}

                override fun onDataChange(dataSnapshot: DataSnapshot) {

                    mCoffees.clear()    // clear list before adding data

                    for(children in dataSnapshot.children) {    // loop child of "coffee" for get data

                        // add data to list
                        mCoffees.add(

                            CoffeeOrder(
                                children.child("name").getValue(String::class.java),
                                children.child("price").getValue(Int::class.java)!!,
                                0,
                                false
                            )

                        )

                    }

                    mView.coffeeMenu(mCoffees)  // serve data's to view
                    mView.hideLoading()

                }

            })

        } catch (exc: FirebaseException) {
            Log.e(TAG, "Handle error on: ${exc.message}")
        } catch (exc: NullPointerException) {
            Log.e(TAG, "Handle error on: ${exc.message}")
        }

    }

}