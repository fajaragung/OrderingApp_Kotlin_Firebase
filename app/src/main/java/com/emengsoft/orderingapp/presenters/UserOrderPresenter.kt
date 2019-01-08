package com.emengsoft.orderingapp.presenters

import android.util.Log
import com.emengsoft.orderingapp.contracts.UserOrderContract
import com.emengsoft.orderingapp.models.CoffeeOrder
import com.emengsoft.orderingapp.models.UserOrder
import com.google.firebase.FirebaseException
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

/**
 * Created by Fajar Agung Pramana on 07 January 2019
 * - Emengsoft Studio
 * - Indonesia
 */
 
class UserOrderPresenter : UserOrderContract.Presenter {

    companion object {
        private const val TAG = "UserOrderPresenter"
    }

    private lateinit var mViewSetOrder: UserOrderContract.SetOrder
    private lateinit var mViewGetOrder: UserOrderContract.GetOrder

    private val mUserOrders = mutableListOf<UserOrder>()

    constructor(mViewSetOrder: UserOrderContract.SetOrder) {
        this.mViewSetOrder = mViewSetOrder
    }

    constructor(mViewGetOrder: UserOrderContract.GetOrder) {
        this.mViewGetOrder = mViewGetOrder
    }

    override fun setUserOrders(username: String?, table: String?, date: String?, orders: List<CoffeeOrder>) {

        // reference firebase
        val dbRef = FirebaseDatabase.getInstance().reference
            .child("userorder")
            .push()

        // set data to model
        val userOrder = UserOrder(username, table, date, orders)

        try {

            dbRef.child("name").setValue(userOrder.username)
            dbRef.child("table").setValue(userOrder.table)
            dbRef.child("date").setValue(userOrder.date)
            dbRef.child("orders").setValue(userOrder.orders)

            mViewSetOrder.orderSuccessfull()

        } catch (exc: FirebaseException) {
            Log.e(TAG, "Handle error: ${exc.message}")
            mViewSetOrder.orderFailure()
        } catch (exc: NullPointerException) {
            Log.e(TAG, "Handle error: ${exc.message}")
            mViewSetOrder.orderFailure()
        }

    }

    override fun getUserOrders() {

        // reference firebase
        val dbRef = FirebaseDatabase.getInstance().reference
            .child("userorder")

        try {

            mViewGetOrder.showLoading()

            dbRef.addValueEventListener(object : ValueEventListener {

                override fun onCancelled(databaseError: DatabaseError) {}

                override fun onDataChange(dataSnapshot: DataSnapshot) {

                    mUserOrders.clear()

                    for(children in dataSnapshot.children) {

                        mUserOrders.add(
                            UserOrder(
                                children.child("name").getValue(String::class.java),
                                children.child("table").getValue(String::class.java),
                                children.child("date").getValue(String::class.java),
                                (children.child("orders").value as List<CoffeeOrder>
                                        )
                            ))

                    }

                    mViewGetOrder.userOrders(mUserOrders)
                    mViewGetOrder.hideLoading()

                }

            })

        }  catch (exc: FirebaseException) {
            Log.e(TAG, "Handle error: ${exc.message}")
            mViewSetOrder.orderFailure()
        } catch (exc: NullPointerException) {
            Log.e(TAG, "Handle error: ${exc.message}")
            mViewSetOrder.orderFailure()
        }

    }

}