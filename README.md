![build](https://github.com/ibrahimyilmaz/kiel/workflows/build/badge.svg)
[ ![Download](https://api.bintray.com/packages/ibrahimyilmaz/kiel/kiel/images/download.svg) ](https://bintray.com/ibrahimyilmaz/kiel/kiel/_latestVersion)
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Kiel-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/8140)
## Kiel

Kiel is a `RecyclerView.Adapter` with a minimalistic and convenient Kotlin DSL which provides utility on top of Android's normal `RecyclerView.Adapter`.

<img alt="kiel_icon" src="art/kiel_icon.svg" width="250">

Most of the time:
- We found ourselves repeating same boilerplate code for `RecyclerView.Adapter`.
- We have difficulty in handling `RecyclerView.Adapter` when there are many `viewTypes`.

But now, Kiel may help us to get rid of these problems.

## Usage:

#### Basic Usage:

##### adapterOf
```kt
 val recyclerViewAdapter = adapterOf<Text> {
                register(
                    layoutResource = R.layout.adapter_message_text_item,
                    viewHolder = ::TextMessageViewHolder,
                    onViewHolderCreated = { vh->
                       //you may handle your on click listener
                       vh.itemView.setOnClickListener {

                       }
                    },
                    onBindViewHolder = { vh, _, it ->
                        vh.messageText.text = it.text
                        vh.sentAt.text = it.sentAt
                    }
                )
     }

 recyclerView.adapter = recyclerViewAdapter
 ```

##### pagingDataAdapterOf
```kt
 val pagingDataAdapterOf = pagingDataAdapterOf<Text> {
                register(
                    layoutResource = R.layout.adapter_message_text_item,
                    viewHolder = ::TextMessageViewHolder,
                    onViewHolderCreated = { vh->
                       //you may handle your on click listener
                       vh.itemView.setOnClickListener {

                       }
                    },
                    onBindViewHolder = { vh, _, it ->
                        vh.messageText.text = it.text
                        vh.sentAt.text = it.sentAt
                    }
                )
     }

 recyclerView.adapter = recyclerViewAdapter
 ```


#### Different View Types:

You may register different `ViewHolder`s to your adapters.

```kt
              register(
                    layoutResource = R.layout.adapter_message_text_item,
                    viewHolder = ::TextMessageViewHolder,
                    onBindViewHolder = { vh, _, it ->
                        vh.messageText.text = it.text
                        vh.sentAt.text = it.sentAt
                    }
                )

                register(
                    layoutResource = R.layout.adapter_message_image_item,
                    viewHolder = ::ImageMessageViewHolder,
                    onBindViewHolder = { vh, _, item ->
                        vh.messageText.text = item.text
                        vh.sentAt.text = item.sentAt

                        Glide.with(vh.messageImage)
                            .load(item.imageUrl)
                            .into(vh.messageImage)
                    }
                )
```
#### Handling Events:

As `ViewHolder` instance is accessible in:
- `onViewHolderCreated`
- `onBindViewHolder`
- `onBindViewHolderWithPayload`


You can handle the events in the same way how you did it before.
```kt
 val recyclerViewAdapter = adapterOf<Text> {
                register(
                    layoutResource = R.layout.adapter_message_text_it,
                    viewHolder = ::TextMessageViewHolder,
                    onViewHolderCreated = { vh->
                       vh.itemView.setOnClickListener {

                       }
                       vh.messageText.addTextChangedListener{text ->

                       }
                    },
                    onBindViewHolder = { vh, _, it ->
                        vh.messageText.text = it.text
                        vh.sentAt.text = it.sentAt
                    }
                )
 }

recyclerView.adapter = recyclerViewAdapter
```
#### View Binding:

As `ViewHolder` instance is accessible in:
- `onViewHolderCreated`
- `onBindViewHolder`
- `onBindViewHolderWithPayload`

You may define your ViewBinding in your ViewHolder class and you can easily reach it:

```kt

class TextMessageViewHolder(view: View) : RecyclerViewHolder<Text>(view) {
    val binding = AdapterTextItemBinding.bind(view)
}

val recyclerViewAdapter = adapterOf<Text> {
                register(
                    layoutResource = R.layout.adapter_message_text_it,
                    viewHolder = ::TextMessageViewHolder,
                    onViewHolderCreated = { vh->
                       vh.binding.
                    },
                    onBindViewHolder = { vh, _, it ->
                       vh.binding.messageText.text = it.text
                       vh.binding.sentAt.text = it.sentAt
                    }
                )
 }
```

#### DiffUtil:

```kt
val recyclerViewAdapter = adapterOf<MessageViewState> {
                diff(
                    areContentsTheSame = { old, new -> old == new },
                    areItemsTheSame = { old, new -> old.message.id == new.message.id },
                    getChangePayload = { oldItem, newItem ->
                        val diffBundle = Bundle()

                        if (oldItem.selectionState != newItem.selectionState) {
                            diffBundle.putParcelable(
                                TextMessageViewHolder.KEY_SELECTION,
                                newItem.selectionState
                            )
                        }

                        if (diffBundle.isEmpty) null else diffBundle
                    }
                )
                register (
                    layoutResource = R.layout.adapter_message_text_item,
                    viewHolder = ::TextMessageViewHolder,
                    onBindViewHolder = { vh, _, it ->
                        vh.messageText.text = it.message.text
                        vh.sentAt.text = it.message.sentAt
                    }
                )

```
Download
--------

```groovy
implementation 'me.ibrahimyilmaz:kiel:latestVersion'
```

License
-------
```
Copyright 2020 Ibrahim Yilmaz

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
